package com.sqq.sqq_total.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.TcpService;
import com.sqq.sqq_total.data.Type;
import com.sqq.sqq_total.data.userBeen;
import com.sqq.sqq_total.presenter.LoginPresenter;
import com.sqq.sqq_total.view.Dialog;

/**
 * Created by sqq on 2016/8/10.
 * 这里逻辑简单就不再使用mvp模式
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginPresenter.LoginView{

    Toolbar mToolbar;

    LoginPresenter lp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.sign_in_button);
        login.setOnClickListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.log_label));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lp = new LoginPresenter(this);
        addSubscription(lp.registerResponseListener(this));
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                AutoCompleteTextView userId = (AutoCompleteTextView) findViewById(R.id.userid);
                EditText password = (EditText) findViewById(R.id.password);

                lp.attemptLogin(this,userId,password);
                break;
        }
    }


    @Override
    public void LogindError() {
        //弹窗提示错误，弹窗还不是很理想
        Dialog.showDialog(this,"",getString(R.string.log_loginerror),true,2000);
    }

    @Override
    public void LoginSuccess() {
        finishActivity();
    }

    @Override
    public void showProgress() {
        Button login = (Button) findViewById(R.id.sign_in_button);
        login.setText(R.string.log_logining);
    }

    @Override
    public void hideProgress() {
        Button login = (Button) findViewById(R.id.sign_in_button);
        login.setText(R.string.log_label);
    }

    @Override
    public void netWorkError() {
        //弹窗提示错误，弹窗还不是很理想
        Dialog.showDialog(this,"",getString(R.string.log_login_network_error),true,2000);
    }
}
