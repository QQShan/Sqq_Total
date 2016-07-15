package com.sqq.sqq_total.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.ui.fragment.BaseFragment;
import com.sqq.sqq_total.view.BrowserLayout;

/**
 * Created by Administrator on 2016/6/6.
 */
public class HeadlineActivity extends BaseActivity{

    BrowserLayout bl;
    Toolbar mToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline);
        String url = getIntent().getExtras().getString(BaseFragment.bundleURL,"");
        String title = getIntent().getExtras().getString(BaseFragment.bundleTITLE,"");
        bl = (BrowserLayout) findViewById(R.id.act_headline_bl);
        if(!url.equals(""))
            bl.loadUrl(url);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finishActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
