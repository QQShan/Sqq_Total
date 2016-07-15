package com.sqq.sqq_total;

import android.app.Application;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sqq.sqq_total.utils.WriteApk;

import java.io.File;

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
        String path= Environment.getExternalStorageDirectory().getPath()+"/app-release.apk";
        File file = new File(path);
        WriteApk.writeApk(file,"你好啊!");

        Log.d("dpi","x:"+getResources().getDisplayMetrics().xdpi
                +"y:"+getResources().getDisplayMetrics().ydpi);
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
