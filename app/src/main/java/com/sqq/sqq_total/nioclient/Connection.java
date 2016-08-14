package com.sqq.sqq_total.nioclient;

import com.sqq.sqq_total.rxbus.ConnectedEvent;
import com.sqq.sqq_total.rxbus.RxBus;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by sqq on 2016/8/9.
 */
public class Connection {

    private boolean isConnected;
    private int connectionId;

    /**
     * 连接超时时间
     */
    private int connectTimeout;
    private InetAddress connectHost;
    private int connectTcpPort;
    private Selector selector;
    private SelectionKey selectionKey;
    private SocketChannel clientChannel;

    public Connection(int connectTimeout, InetAddress connectHost, int connectTcpPort) throws IOException {
        this.connectTimeout = connectTimeout;
        this.connectHost = connectHost;
        this.connectTcpPort = connectTcpPort;
        isConnected = false;
        initConnect();
    }

    public void initConnect() throws IOException {
        clientChannel = SocketChannel.open();
        Socket socket = clientChannel.socket();
        //确保数据及时发出
        socket.setTcpNoDelay(true);
        //表示发送请求之后10s内没有接受到数据就断开客户端
        //socket.setSoTimeout(10000);
        //下面两句不能改变顺序，为了方便就直接是阻塞模式连接，连接之后再设置非阻塞模式
        socket.connect(new InetSocketAddress(connectHost, connectTcpPort), connectTimeout);
        //boolean connect = clientChannel.connect(new InetSocketAddress(connectHost, connectTcpPort));
        clientChannel.configureBlocking(false);

        try {
            selector = Selector.open();
        } catch (IOException ex) {
            throw new RuntimeException("Error opening selector.", ex);
        }

        if (socket.isConnected()) {
            //已经连接
            selectionKey = clientChannel.register(selector, SelectionKey.OP_READ);
            isConnected = true;
            RxBus.getRxBus().send(new ConnectedEvent());
        } else {
            //这句其实执行不到的
            selectionKey = clientChannel.register(selector, SelectionKey.OP_CONNECT);
        }

    }

    public Selector getSelector() {
        return selector;
    }

    public SelectionKey getSelectionKey(){
        return selectionKey;
    }

    public void close() {
        isConnected = false;
        try {
            if (selector != null) {
                selector.close();
                selector = null;
            }
            if (clientChannel != null)
                clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getConnectionId() {
        return connectionId;
    }

    /**
     * 这个连接是否还连接着
     *
     * @return
     */
    public boolean isConnected() {
        return isConnected;
    }

}
