package com.sqq.sqq_total.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.presenter.UploadPicPresenter;

/**
 * Created by sqq on 2016/7/13.
 */
public class UploadPicActivity extends BaseActivity implements UploadPicPresenter.UploadPicView,
        View.OnClickListener {


    UploadPicPresenter upp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpic);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.setting_uploadpic);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        upp = new UploadPicPresenter(this);

        Button bt = (Button) findViewById(R.id.takephoto);
        bt.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void uploadsuccess() {
        finishActivity();
    }

    @Override
    public void uploadfail() {

    }

    @Override
    public void intentToForResult(Intent intent,int requestCode) {
        startForResult(intent,requestCode);
    }

    @Override
    public void toClip(Intent intent) {
        //goToWithInfo(MetoActivity,intent);
        EditText et = (EditText) findViewById(R.id.upload_title);
        if(!TextUtils.isEmpty(et.getText().toString())){
            addSubscription(upp.uploadPic(this,et.getText().toString()));
        }else{
            addSubscription(upp.uploadPic(this,""));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        upp.handlerReturn(requestCode,resultCode,data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takephoto:
                upp.takePhoto(this);
                break;
        }
    }
}
