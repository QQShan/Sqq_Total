package com.sqq.sqq_total.nioclient;


import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by sqq on 2016/8/10.
 * 想来想去还是应该用一个管理类来管理连接池
 * <p/>
 * 这个类的作用就是减轻NioClient这个类的压力
 * <p/>
 * 把以下功能分到这个类中
 * 1、创建连接池
 * 2、维持连接池中的连接的数量
 */
public class PoolManager implements Runnable {

    final String TAG = "NioClient-PoolManager";
    /**
     * 连接超时时间
     */
    private int connectTimeout;
    private InetAddress connectHost;
    private int connectTcpPort;
    /**
     * true就是不清空
     * false 就清空
     */
    private boolean poolFlag;

    ConnectionsPool pool;
    int poolSize;

    public PoolManager(int connectTimeout, InetAddress connectHost, int connectTcpPort, int poolSize) throws IOException {
        this.connectTimeout = connectTimeout;
        this.connectHost = connectHost;
        this.connectTcpPort = connectTcpPort;
        this.poolSize = poolSize;

        pool = new ConnectionsPool(poolSize);
        init();
    }

    private void init() throws IOException {
        while (pool.getTruePoolSize() != poolSize) {
            Connection connection = null;
            connection = new Connection(connectTimeout, connectHost, connectTcpPort);

            pool.putConnection(connection);
        }
        poolFlag = true;

        Thread managerThread = new Thread(this);
        managerThread.setDaemon(true);
        managerThread.start();
    }

    @Override
    public void run() {
        while(poolFlag){
            if(pool.getTruePoolSize()==poolSize){
                continue;
            }
            Log.d(TAG, "size" + pool.getTruePoolSize());
            while (pool.getTruePoolSize() != poolSize) {

                Connection connection = null;
                try {
                    connection = new Connection(connectTimeout, connectHost, connectTcpPort);
                    pool.putConnection(connection);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "创建连接失败");
                }

            }
        }

        pool.clear();
    }

    /**
     * 关闭所有连接，清空连接池
     */
    public void clearPool() {
        poolFlag = false;
    }

    public Connection getConnection() {
        return pool.getConnection();
    }
}
