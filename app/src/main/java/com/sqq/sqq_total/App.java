package com.sqq.sqq_total;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by sqq on 2016/5/28.
 */
public class App extends Application {
    private static final Object monitor = new Object();
    private static SqqRetrofit retrofit;
    public static App appInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    public static App getAppInstance(){
        return appInstance;
    }

    public static SqqRetrofit getRetrofitInstance(){
        synchronized (monitor) {
            if (retrofit == null) {
                retrofit = new SqqRetrofit(appInstance);
            }
            return retrofit;
        }
    }
}
