package com.sqq.sqq_total.ui.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.servicedata.HeadlineItem;
import com.sqq.sqq_total.utils.FileLoader;
import com.sqq.sqq_total.utils.TimerUtils;
import com.sqq.sqq_total.utils.WriteApk;
import com.sqq.sqq_total.view.LoadingView;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sqq on 2016/5/30.
 */
public class RightFragment extends BaseFragment {

    Subscription s;
    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_right, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getSelfActivity()).setSupportActionBar(toolbar);
        final TextView tv = (TextView) rootView.findViewById(R.id.files);
        /*String path = Environment.getExternalStorageDirectory().getPath();
        String[] suffix={"txt"};
        List ret = FileLoader.GetFileWithSuffix(path + File.separator*//*+"360"*//*, suffix);
        String xx="";
        for(int  i=0;i<ret.size();i++){
            xx+=ret.get(i);
            Log.d("xxx",ret.get(i)+"");
        }
        tv.setText(xx);*/

        /*final LoadingView lv = new LoadingView(getSelfActivity());
        lv.showDialog("正在加载。。。");*/

        s = App.getRetrofitInstance().getApiService()
                //.setDate(TimerUtils.getTimeStampLong())
                .getLatestItemInfo(1)
                .map(new Func1<List<HeadlineItem>, List<HeadlineItem>>() {
                    @Override
                    public List<HeadlineItem> call(List<HeadlineItem> headlineItems) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return headlineItems;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HeadlineItem>>() {
                    @Override
                    public void onCompleted() {
                        //lv.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("sqqq", throwable.toString());
                    }

                    @Override
                    public void onNext(List<HeadlineItem> s) {
                        //Log.d("ret", s.get(0).getDescription() + " " + s.get(0).getPicUrl() + " "
                        //        + s.get(0).getTitle() + " " + s.get(0).getUrl() + " " + TimerUtils.longTimeparseToString(s.get(0).getTime()));
                        /*if(s){
                            tv.setText("可以");
                        }
                        else{
                            tv.setText("不可以");
                        }*/
                        /*tv.setText(s.getDescription()+" "+s.getPicUrl()+" "
                        +s.getTitle()+" "+s.getUrl()+ " "+TimerUtils.longTimeparseToString(s.getTime()));*/
                        tv.setText(TimerUtils.getTimeUpToNow(s.get(0).getTime(),getSelfActivity()));
                        File file = new File(WriteApk.getPackagePath(getSelfActivity()));
                        tv.setText(WriteApk.readApk(file));
                    }
                });

        addSubscription(s);

        /*lv.setLoadExitListener(new LoadingView.LoadExit() {
            @Override
            public void exit() {
                s.unsubscribe();
            }
        });*/
    }
}
