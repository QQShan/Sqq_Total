package com.sqq.sqq_total.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.ui.fragment.BaseFragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/6/7.
 */
public class BaseActivity extends AppCompatActivity {

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }


    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        if(!s.isUnsubscribed()) {
            //如果没有被unsubscribed
            this.mCompositeSubscription.add(s);
        }
    }

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

    protected void intentTo(String title, String url,long id,Class<?> actc) {
        Bundle bd= new Bundle();
        bd.putString(BaseFragment.bundleURL, url);
        bd.putString(BaseFragment.bundleTITLE, title);
        bd.putLong(BaseFragment.bundleID, id);
        goToWithInfo(actc, bd);
    }

    protected void intentTo(String title, String url,Class<?> actc) {
        Bundle bd= new Bundle();
        bd.putString(BaseFragment.bundleURL, url);
        bd.putString(BaseFragment.bundleTITLE, title);
        goToWithInfo(actc, bd);
    }

    protected void intentTo(String title,Class<?> actc) {
        Bundle bd= new Bundle();
        bd.putString(BaseFragment.bundleTITLE, title);
        goToWithInfo(actc, bd);
    }

    public void startForResult(Intent intent,int requestCode){
        startActivityForResult(intent, requestCode);
        overridePendingTransition(
                R.animator.finish_anim,
                R.animator.finish_noanim
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
