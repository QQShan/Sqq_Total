package com.sqq.sqq_total.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.view.BrowserLayout;

/**
 * Created by Administrator on 2016/6/6.
 */
public class HeadlineActivity extends BaseActivity{

    BrowserLayout bl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline);

        bl = (BrowserLayout) findViewById(R.id.act_headline_bl);
        bl.setTitleText("头条啊！");
        bl.setOnImgClickListener(new BrowserLayout.OnImgClickCallback() {
            @Override
            public void OnImgClick() {
                finishActivity();
            }
        });
        bl.loadUrl("http://10.0.3.114/AndroidService/pic/1.jpg");
    }


}
