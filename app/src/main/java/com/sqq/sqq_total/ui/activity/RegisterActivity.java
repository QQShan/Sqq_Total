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

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.presenter.RegisterPresenter;
import com.sqq.sqq_total.view.Dialog;

/**
 * Created by sqq on 2016/8/10.
 * 这里直接用了登录的页面，
 * 因为没有想好注册页怎么布局
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener,RegisterPresenter.RegisterView {

    Toolbar mToolbar;

    RegisterPresenter rp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.sign_in_button);
        login.setText(getString(R.string.reg_label));

        login.setOnClickListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.reg_label));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rp = new RegisterPresenter(this);
        addSubscription(rp.registerResponseListener(this));
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

                rp.attemptReg(this,userId,password);
                break;
        }
    }



    @Override
    public void regError() {
        //弹窗提示错误，弹窗还不是很理想
        Dialog.showDialog(this, "", getString(R.string.reg_regerror), true, 2000);
    }

    @Override
    public void regSuccess() {
        finishActivity();
    }

    @Override
    public void showProgress() {
        Button login = (Button) findViewById(R.id.sign_in_button);
        login.setText(R.string.reg_reging);
    }

    @Override
    public void hideProgress() {
        Button login = (Button) findViewById(R.id.sign_in_button);
        login.setText(R.string.reg_label);
    }

    @Override
    public void netWorkError() {
        //弹窗提示错误，弹窗还不是很理想
        Dialog.showDialog(this,"",getString(R.string.log_login_network_error),true,2000);
    }
}
