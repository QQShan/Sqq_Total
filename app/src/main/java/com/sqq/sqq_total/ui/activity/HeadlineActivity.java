package com.sqq.sqq_total.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.ui.fragment.BaseFragment;
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
        String url = getIntent().getExtras().getString(BaseFragment.bundleURL);
        String title = getIntent().getExtras().getString(BaseFragment.bundleTITLE);
        bl = (BrowserLayout) findViewById(R.id.act_headline_bl);
        bl.setTitleText(title);
        bl.setOnImgClickListener(new BrowserLayout.OnImgClickCallback() {
            @Override
            public void OnImgClick() {
                finishActivity();
            }
        });
        bl.loadUrl(url);
    }


}
