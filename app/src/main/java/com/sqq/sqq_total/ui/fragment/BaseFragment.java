package com.sqq.sqq_total.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.ui.activity.HeadlineActivity;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sqq on 2016/5/28.
 */
public abstract class BaseFragment extends Fragment {

    public static final String bundleURL = "url";
    public static final String bundleTITLE = "title";
    protected ViewGroup rootView;
    protected WeakReference activityRef;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("fragment", "onAttach");
        activityRef = new WeakReference((Activity)context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fragment", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("fragment", "onCreateView");
        if(null==rootView){
            ifNotNUll(inflater, container, savedInstanceState);
        }
        return rootView;
        /*return super.onCreateView(inflater, container, savedInstanceState);*/
    }

    protected abstract void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("fragment", "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("fragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("fragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("fragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("fragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("fragment", "onDestroyView");
        if(null!=rootView){
            ViewGroup gp = (ViewGroup) rootView.getParent();
            if(gp!=null)
                gp.removeView(rootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    protected Activity getSelfActivity(){

        if(activityRef!=null){
            return (Activity)activityRef.get();
        }
        return null;
    }

    protected void goTo(Class<?> actc){
        Intent intent = new Intent();
        intent.setClass(getSelfActivity(), actc);
        getSelfActivity().startActivity(intent);
        getSelfActivity().overridePendingTransition(
                R.animator.in_anim,
                R.animator.out_anim
        );
    }

    protected void goToWithInfo(Class<?> actc, Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(getSelfActivity(), actc);
        if(null!=bundle){
            intent.putExtras(bundle);
        }
        getSelfActivity().startActivity(intent);
        getSelfActivity().overridePendingTransition(
                R.animator.in_anim,
                R.animator.out_anim
        );
    }
}
