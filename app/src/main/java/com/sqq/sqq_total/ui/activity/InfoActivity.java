package com.sqq.sqq_total.ui.activity;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.data.Type;
import com.sqq.sqq_total.data.userBeen;
import com.sqq.sqq_total.databaseDao.User;
import com.sqq.sqq_total.databasehelper.DbUtil;
import com.sqq.sqq_total.presenter.InfoPresenter;
import com.sqq.sqq_total.ui.fragment.BaseFragment;
import com.sqq.sqq_total.utils.DestinyUtils;
import com.sqq.sqq_total.utils.PreferenceUtils;
import com.sqq.sqq_total.view.BrowserLayout;
import com.sqq.sqq_total.view.Dialog;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */
public class InfoActivity extends BaseActivity implements InfoPresenter.infoview{

    InfoPresenter ip;
    Toolbar mToolbar;
    ViewStub logined,nologin;
    View vs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.drawer_info));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logined = null;
        nologin = null;

        ip = new InfoPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public void loadLoginedView(User user) {
        if(logined==null){
            logined = (ViewStub) findViewById(R.id.login);
            vs =  logined.inflate();
            if(nologin!=null)
                nologin.setVisibility(View.GONE);
        }

        if(user!=null){
            TextView tvs = (TextView) vs.findViewById(R.id.sex);
            tvs.setText(getString(R.string.info_sex)+":"+(user.getSex()==0?getString(R.string.info_woman):getString(R.string.info_man)));

            TextView tva = (TextView) vs.findViewById(R.id.age);
            tva.setText(getString(R.string.info_age)+":"+user.getAge());

            if(!user.getPicUrl().equals("")) {
                ImageView iv = (ImageView) findViewById(R.id.img_face);
                Picasso.with(this).load(user.getPicUrl()).into(iv);
            }
        }


        click(R.id.sex, vs);
        click(R.id.age, vs);
        click(R.id.albums, vs);
        click(R.id.loginout, vs);
    }

    @Override
    public void loadNoLoginView() {
        if(nologin==null){
            nologin = (ViewStub) findViewById(R.id.nologin);
            vs =  nologin.inflate();
        }

        TextView tv = (TextView) vs.findViewById(R.id.nologintext);
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //选择登录还是注册
                final AlertDialog d = new AlertDialog.Builder(InfoActivity.this).create();
                d.show();
                Window window = d.getWindow();
                window.setContentView(R.layout.viewofwindow);
                window.setLayout(DestinyUtils.getScreenWidth(InfoActivity.this),
                        DestinyUtils.getScreenHeight(InfoActivity.this) / 4);
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.sqqDialogStyle);

                Button bt_reg = (Button) window.findViewById(R.id.bt_reg);
                bt_reg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goTo(RegisterActivity.class);
                        if(d!=null){
                            d.cancel();
                        }
                    }
                });

                Button bt_log = (Button) window.findViewById(R.id.bt_log);
                bt_log.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goTo(LoginActivity.class);
                        if(d!=null){
                            d.cancel();
                        }
                    }
                });

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
                        PreferenceUtils.saveString(InfoActivity.this, R.string.prefer_token, "");
                        PreferenceUtils.saveLong(InfoActivity.this, R.string.prefer_userId, -1L);
                        finish();
                        goTo(InfoActivity.class);
                        break;
                }
            }
        });
    }
}
