package com.sqq.sqq_total.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.sqq.sqq_total.R;

/**
 * Created by Administrator on 2016/6/7.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finishActivity();
        }
        return false;
    }

    protected void goToWithInfo(Class<?> actc, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, actc);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(
                R.animator.in_anim,
                R.animator.out_anim
        );
    }
    protected void goTo(Class<?> actc){
        Intent intent = new Intent();
        intent.setClass(this, actc);
        startActivity(intent);
        overridePendingTransition(
                R.animator.in_anim,
                R.animator.out_anim
        );
    }

    protected void finishActivity(){
        finish();
        overridePendingTransition(
                R.animator.finish_anim,
                R.animator.finish_noanim
        );
    }
}
