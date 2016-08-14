package com.sqq.sqq_total.nioclient;

import android.util.SparseArray;

/**
 * Created by sqq on 2016/8/9.
 * 连接池
 * 根据官网性能优化
 * 在数量少的时候使用ArrayMap代替hashmap
 * 另外为了避免自动装箱的情况使用SparseArray
 *
 * 这里的打算是这个连接池
 *
 * 这里考虑到连接池的数量不会太多，在代码的编写上也偷了个懒
 */
public class ConnectionsPool {

    SparseArray<Connection> sparseArray;
    private int poolSize;

    public ConnectionsPool(int poolSize){
        sparseArray = new SparseArray<>();
        this.poolSize = poolSize;
    }

    public Connection getConnection(){
        Connection connection = null;

        for(int i=0;i<sparseArray.size();i++){
            //在这里做连接池的清理，方便
            connection = sparseArray.get(i);
            if (connection.isConnected()){
                break;
            }

        }
        return connection;
    }

    /**
     * 失败的原因：超过池的大小，要加入的连接已经断开
     * @param connection
     * @return
     */
    public boolean putConnection(Connection connection){
        if(!connection.isConnected()){
            return false;
        }
        if(sparseArray.size()==poolSize){
            for(int i=0;i<sparseArray.size();i++){
                if(!sparseArray.get(i).isConnected()){
                    sparseArray.put(i,connection);
                    return true;
                }
            }
            return false;
        }else if(sparseArray.size()<poolSize){
            sparseArray.put(sparseArray.size(),connection);
        }
        return true;
    }

    /**
     * 获取连接池中连接的个数
     * @return
     */
    public int getTruePoolSize(){
        int ret=0;
        for(int i=0;i<sparseArray.size();i++){
            if(sparseArray.get(i).isConnected()){
               ++ret;
            }
        }
        return ret;
    }

    public void clear(){
        for(int i=0;i<sparseArray.size();i++){
            sparseArray.get(i).close();
        }
        sparseArray.clear();
    }
}
