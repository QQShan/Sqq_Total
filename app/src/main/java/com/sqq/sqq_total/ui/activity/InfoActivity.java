package com.sqq.sqq_total.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.ui.fragment.BaseFragment;
import com.sqq.sqq_total.view.BrowserLayout;

/**
 * Created by Administrator on 2016/7/8.
 */
public class InfoActivity extends BaseActivity {

    Toolbar mToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.drawer_info));
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
