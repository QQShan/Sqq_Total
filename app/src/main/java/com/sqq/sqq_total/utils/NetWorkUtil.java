package com.sqq.sqq_total.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sqq.sqq_total.receiver.NetWorkReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class NetWorkUtil {
    private static final Object monitor = new Object();

    static NetWorkUtil network;
    private List<NetworkListener> networklisteners;

    public interface NetworkListener {

        void onConnected(boolean collect);
    }

    public static NetWorkUtil getInstance(){
        synchronized (monitor) {
            if (network == null) {
                network = new NetWorkUtil();
            }

            return network;
        }
    }

    public void addNetWorkListener(NetworkListener networkreceiver) {
        if (null == networklisteners) {
            networklisteners = new ArrayList<>();
        }
        networklisteners.add(networkreceiver);
    }

    public void removeNetWorkListener(NetworkListener listener) {
        if (networklisteners != null) {
            networklisteners.remove(listener);
        }
    }

    public void clearNetWorkListeners() {
        if (networklisteners != null) {
            networklisteners.clear();
        }
    }


    private boolean isConnected = true;

    /**
     * 获取是否连接
     */
    public boolean isConnected() {
        return isConnected;
    }

    private void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * 判断网络连接是否存在
     *
     * @param context
     */
    public void setConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            setConnected(false);


            if (networklisteners != null) {
                for (int i = 0, z = networklisteners.size(); i < z; i++) {
                    NetworkListener listener = networklisteners.get(i);
                    if (listener != null) {
                        listener.onConnected(false);
                    }
                }
            }

        }

        NetworkInfo info = manager.getActiveNetworkInfo();

        boolean connected = info != null && info.isConnected();
        setConnected(connected);

        if (networklisteners != null) {
            for (int i = 0, z = networklisteners.size(); i < z; i++) {
                NetworkListener listener = networklisteners.get(i);
                if (listener != null) {
                    listener.onConnected(connected);
                }
            }
        }

    }
}
