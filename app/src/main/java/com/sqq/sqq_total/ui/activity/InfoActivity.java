package com.sqq.sqq_total.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.presenter.InfoPresenter;
import com.sqq.sqq_total.ui.fragment.BaseFragment;
import com.sqq.sqq_total.utils.PreferenceUtils;
import com.sqq.sqq_total.view.BrowserLayout;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/7/8.
 */
public class InfoActivity extends BaseActivity implements InfoPresenter.infoview{

    InfoPresenter ip;
    Toolbar mToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.drawer_info));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ip = new InfoPresenter(this);
        ip.initView(this);
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

    @Override
    public void loadLoginedView() {
        ViewStub logined = (ViewStub) findViewById(R.id.login);
        View vs =  logined.inflate();
        click(R.id.sex, vs);
        click(R.id.age, vs);
        click(R.id.albums, vs);
        click(R.id.loginout, vs);
    }

    @Override
    public void loadNoLoginView() {
        ViewStub nologin = (ViewStub) findViewById(R.id.nologin);
        View vs =  nologin.inflate();

        TextView tv = (TextView) vs.findViewById(R.id.nologintext);
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到登录注册页
            }
        });
    }

    public void click(int id,View vs){
        TextView tv=null;
        switch (id){
            case R.id.sex:
                tv = (TextView) vs.findViewById(R.id.sex);
                break;
            case R.id.age:
                tv = (TextView) vs.findViewById(R.id.age);
                break;
            case R.id.albums:
                tv = (TextView) vs.findViewById(R.id.albums);
                break;
            case R.id.loginout:
                tv = (TextView) vs.findViewById(R.id.loginout);
                break;
        }
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到相应的页面
                switch (v.getId()){
                    case R.id.sex:
                        Log.d("sqq","click");
                        break;
                    case R.id.age:
                        break;
                    case R.id.albums:
                        break;
                    case R.id.loginout:
                        break;
                }
            }
        });
    }
}
