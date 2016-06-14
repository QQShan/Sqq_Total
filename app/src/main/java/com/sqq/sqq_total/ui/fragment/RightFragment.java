package com.sqq.sqq_total.ui.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.servicedata.HeadlineItem;
import com.sqq.sqq_total.utils.FileLoader;

import java.io.File;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/30.
 */
public class RightFragment extends BaseFragment {

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_right, container, false);
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

        Subscription s = App.getRetrofitInstance().getApiService()
                .getLatestItemInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HeadlineItem>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("sqqq",throwable.toString());
                    }

                    @Override
                    public void onNext(HeadlineItem s) {
                        tv.setText(s.getDescription()+" "+s.getPicUrl()+" "
                        +s.getTitle()+" "+s.getUrl()+ " "+s.getTime());
                    }
                });
        addSubscription(s);
    }
}
