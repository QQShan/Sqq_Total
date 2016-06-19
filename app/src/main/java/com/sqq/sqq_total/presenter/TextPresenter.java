package com.sqq.sqq_total.presenter;

import android.content.Context;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.servicedata.TextItem;
import com.sqq.sqq_total.utils.NetWorkUtil;
import com.sqq.sqq_total.view.LoadingView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sqq on 2016/6/17.
 */
public class TextPresenter implements NetWorkUtil.NetworkListener {
    private boolean hasNetwork = true;
    @Override
    public void onConnected(boolean collect) {
        if(collect && !hasNetwork){
            //Log.d("sqqq","有网络,初始化数据");
            tfv.hideErrorView();
            tfv.initData();
            hasNetwork = true;
        }else{
            //没有网络
            //Log.d("sqqq","没有网络");
            hasNetwork =false;
        }
    }
    public interface TextFmView{
        public void initData();
        public void initViews();
        public void intentTo(String title,String url);
        public void getDataError(String info);
        public void hideErrorView();
        public void refresh(boolean isRefreshing);
    }

    TextFmView tfv;
    Subscription s;

    public TextPresenter(TextFmView tfv){
        this.tfv = tfv;
        NetWorkUtil.getInstance().addNetWorkListener(this);
    }

    /**
     * 如果清理会重新加载adapter
     * @param ifClear
     * @param list
     * @return
     */
    public Subscription loadItemData(final boolean ifClear,final List<TextItem> list){

        s = App.getRetrofitInstance().getApiService()
                .getLatestTextItemInfo(AppConfig.textItemCount)
                .map(new Func1<List<TextItem>, List<TextItem>>() {
                    @Override
                    public List<TextItem> call(List<TextItem> textItems) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return textItems;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TextItem>>() {
                    @Override
                    public void onCompleted() {
                        tfv.refresh(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        tfv.getDataError(throwable.toString());
                    }

                    @Override
                    public void onNext(List<TextItem> textItems) {
                        if(ifClear){
                            list.clear();
                            tfv.initViews();
                        }
                        list.addAll(textItems);

                    }
                });

        return s;
    }

    public void unsubscribe(){
        if(!s.isUnsubscribed())
            s.unsubscribe();
    }
}
