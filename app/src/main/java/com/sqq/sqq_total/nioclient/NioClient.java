package com.sqq.sqq_total.nioclient;

import android.util.Log;

import com.sqq.sqq_total.rxbus.DisConnectedEvent;
import com.sqq.sqq_total.rxbus.GetResponseEvent;
import com.sqq.sqq_total.rxbus.RxBus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.security.AccessControlException;
import java.util.Iterator;

/**
 * Created by sqq on 2016/8/9.
 * 稳定性还有待验证
 * 发送消息（send）之后，注册了一个通道的写事件，
 * 如果在进入循环之后，这个通道刚好销毁了，那就没有写事件了
 * 因为行连接池中得到的下一个连接池没有注册写事件
 */
public class NioClient implements Runnable {

    final String TAG = "NioClient";

    static {
        try {
            // Needed for NIO selectors on Android 2.2.
            // 其实就是防止读取到ipv6的地址
            System.setProperty("java.net.preferIPv6Addresses", "false");
        } catch (AccessControlException ignored) {
        }
    }

    final int connectTimeout;
    final InetAddress connectHost;
    final int connectTcpPort;
    final ByteBuffer readBuffer, writeBuffer;
    final int poolSize;
    final Converter converter;

    private boolean clientClosed;
    PoolManager poolManager;

    public NioClient(NioClient.Builder builder) {
        connectTimeout = builder.timeout;
        connectHost = builder.host;
        connectTcpPort = builder.tcpPort;
        writeBuffer = ByteBuffer.allocate(builder.writeBufferSize);
        readBuffer = ByteBuffer.allocate(builder.objectBufferSize);
        poolSize = builder.poolSize;
        converter = builder.converter;

        startClient();
    }

    private void startClient() {
        Thread clientThread = new Thread(this);
        clientThread.setDaemon(true);
        clientThread.start();
    }

    @Override
    public void run() {
        clientClosed = false;
        //这个做法有待优化
        //连接不成功就每隔5s再次尝试
        while(!clientClosed){
            //这里创建连接池是阻塞的，创建成功就返回，不成功就抛出异常
            try {
                poolManager = new PoolManager(connectTimeout, connectHost, connectTcpPort, poolSize);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "连接失败");
                //RxBus.getRxBus().send(new DisConnectedEvent(e.toString()));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }

            //创建好连接池之后开始循环检测数据交换
            checkSelector();
        }

    }

    //必须是throws Exception，不然会在关闭的时候
    // con.getSelector().selectedKeys().iterator()这句报错
    public void checkSelector() {
        while (poolManager!=null && poolManager.getConnection() != null) {
            Connection con = poolManager.getConnection();

            while (con.isConnected()) {
                try {
                    con.getSelector().select();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "select出错" + e.toString());
                    break;
                }

                try {

                    Iterator ite = con.getSelector().selectedKeys().iterator();
                    while (ite.hasNext()) {
                        SelectionKey key = (SelectionKey) ite.next();
                        // 删除已选的key,以防重复处理
                        ite.remove();
                        // 连接事件发生
                        if (key.isConnectable()) {
                            //这里其实不会走到这一步，前面的连接时阻塞的
                            try {
                                connected(key);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d(TAG, "connected出错" + e.toString());
                                continue;
                            }

                        } else if (key.isReadable()) {
                            try {
                                canRead(key);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d(TAG, "read出错" + e.toString());
                                continue;
                            }

                        } else if (key.isWritable()) {
                            try {
                                canWrite(key);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d(TAG, "write出错" + e.toString());
                                continue;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "selectedKeys出错" + e.toString());
                    con.close();
                    break;
                }
            }

            //能走到这里，说明有一个连接断开，需要重新创建一个连接
            //这里我不打算在这个线程中进行连接的重新创建添加
        }

    }

    private void connected(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key
                .channel();
        // 如果正在连接，则完成连接
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        // 设置成非阻塞
        channel.configureBlocking(false);
    }

    private void canRead(SelectionKey key) throws IOException {

        SocketChannel channel = (SocketChannel) key
                .channel();
        int read = channel.read(readBuffer);
        if (read == -1) {
            Log.d(TAG, "连接已经关闭");
            key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
            if(poolManager!=null) {
                poolManager.clearPool();
                poolManager = null;
            }
            RxBus.getRxBus().send(new DisConnectedEvent("连接已经关闭"));
            return;
        }
        //下面这里可以优化，用二进制池，避免经常性的内存开辟
        //好像不用优化，传递的是地址
        byte[] data = readBuffer.array();
        String msg = new String(data,"utf-8").trim();
        String type = converter.getType(msg);
        RxBus.getRxBus().send(new GetResponseEvent(msg,type));
        Log.d(TAG, "read type:"+type);
        //Log.d(TAG, "服务端收到信息：" + msg);
        readBuffer.compact();
        readBuffer.clear();
        //readBuffer.flip();
    }

    private void canWrite(SelectionKey key) throws IOException {
        Log.d(TAG, "write");
        SocketChannel channel = (SocketChannel) key
                .channel();
        //channel.write(ByteBuffer.wrap(new String("客户端:1").getBytes()));
        //判断当前位置到上界(limit)是否还有数据没有写到通道
        while (writeBuffer.hasRemaining()) {
            channel.write(writeBuffer);
            writeBuffer.compact();
            writeBuffer.flip();
        }
        writeBuffer.clear();
        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
    }

    public boolean send(String message) {
        if (poolManager==null||poolManager.getConnection() == null||clientClosed) {
            //没有可用的连接,提示检测网络
            return false;
        }
        Log.d(TAG, message);

        Connection con = poolManager.getConnection();
        SelectionKey selectionKey = con.getSelectionKey();

        try {
            writeBuffer.put(message.getBytes("UTF-8"));
            writeBuffer.flip();

            selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
            con.getSelector().wakeup();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(TAG, "解析失败");
            return false;
        }

        return true;
    }

    public Converter getConverter(){
        return converter;
    }

    /**
     * 关闭所有连接，清空连接池
     */
    public void closeNioClient() {
        clientClosed = true;
        if(poolManager!=null) {
            poolManager.clearPool();
            poolManager = null;
        }
    }

    public boolean isClientClosed(){
        return clientClosed;
    }





    public static final class Builder {
        int writeBufferSize;
        int objectBufferSize;
        /**
         * 默认5s连接超时
         */
        int timeout;
        InetAddress host;
        int tcpPort;
        int poolSize;  //默认5

        /**
         * 转换器
         * 默认用gson
         */
        Converter converter;

        public Builder() {
            writeBufferSize = 8192;
            objectBufferSize = 2048;
            timeout = 5000;
            host = null;
            tcpPort = -1;
            poolSize = 5;
            converter = new GsonConverter();
        }

        public NioClient.Builder setHost(String host) {
            try {
                this.host = InetAddress.getByName(host);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return this;
        }

        public NioClient.Builder setTcpPort(int tcpPort) {
            this.tcpPort = tcpPort;
            return this;
        }

        public NioClient.Builder setPoolSize(int poolSize) {
            this.poolSize = poolSize;
            return this;
        }

        public NioClient.Builder setConverter(Converter converter) {
            this.converter = converter;
            return this;
        }

        public NioClient build() {
            return new NioClient(this);
        }
    }
}
