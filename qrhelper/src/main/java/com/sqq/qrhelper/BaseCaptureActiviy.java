package com.sqq.qrhelper;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;
import com.sqq.qrhelper.view.ViewfinderView;

/**
 * Created by Administrator on 2016/7/12.
 */
public abstract class BaseCaptureActiviy extends AppCompatActivity {

    public abstract void handleDecode(Result result, Bitmap barcode);
    public abstract ViewfinderView getViewfinderView();

    public abstract Handler getHandler();

    public abstract void drawViewfinder();
}
