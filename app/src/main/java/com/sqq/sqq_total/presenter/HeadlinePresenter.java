package com.sqq.sqq_total.presenter;

import android.util.Log;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.servicedata.HeadlineItem;
import com.sqq.sqq_total.utils.TimerUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sqq on 2016/6/16.
 */
public class HeadlinePresenter {
    public interface HeadlineView{
        public void intentTo();
        public void refresh(boolean isRefreshing);
    }

    public HeadlineView view;

    public HeadlinePresenter(HeadlineView view){
        this.view = view;

    }

    public Subscription loadData(final boolean ifClear,final List<HeadlineItem> items){
        Subscription s = App.getRetrofitInstance().getApiService()
                .getLatestItemInfo(AppConfig.headlineItemCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HeadlineItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("sqqq", throwable.toString());
                    }

                    @Override
                    public void onNext(List<HeadlineItem> s) {
                        if(ifClear){
                            items.clear();
                        }
                        Log.d("sqqq",s.size()+"");
                        items.addAll(s);
                        view.refresh(false);
                    }
                });
        return s;
    }
}
