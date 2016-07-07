package com.sqq.sqq_total.presenter;

import android.content.Context;
import android.util.Log;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.servicedata.TextItem;
import com.sqq.sqq_total.utils.NetWorkUtil;
import com.sqq.sqq_total.view.LoadingView;

import java.util.ArrayList;
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
            tfv.startGetData();
            hasNetwork = true;
        }else{
            //没有网络
            //Log.d("sqqq","没有网络");
            hasNetwork =false;
        }
    }
    public interface TextFmView{
        public void startGetData();
        public void initViews(List<TextItem> list);
        public void getDataError(String info);
        public void hideErrorView();
        public void refresh(boolean isRefreshing);
        public void finishLoad(boolean hasData);
        public void loadError();
    }

    TextFmView tfv;
    Subscription s;
    List<TextItem> list_textitem;

    public TextPresenter(TextFmView tfv){
        this.tfv = tfv;
        list_textitem = new ArrayList<>();

        NetWorkUtil.getInstance().addNetWorkListener(this);
        tfv.initViews(list_textitem);
    }

    /**
     * 如果清理会重新加载adapter
     * @return
     */
    public Subscription loadItemData(){

        s = App.getRetrofitInstance().getApiService()
                .getLatestTextItemInfo(AppConfig.textItemCount)
                /*.map(new Func1<List<TextItem>, List<TextItem>>() {
                    @Override
                    public List<TextItem> call(List<TextItem> textItems) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return textItems;
                    }
                })*/
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
                        list_textitem.clear();
                        list_textitem.addAll(textItems);

                    }
                });

        return s;
    }

    /**
     * 加载更多
     */
    public Subscription loadMoreItemData(){
        s = App.getRetrofitInstance().getApiService()
                .getTextItemInfo(AppConfig.textItemCount,list_textitem.get(list_textitem.size()-1).getId())
                .map(new Func1<List<TextItem>, Boolean>() {
                    @Override
                    public Boolean call(List<TextItem> textItems) {
                        if(textItems!=null&&textItems.size()!=0){
                            Log.d("sqqq", "loadmore" + textItems.size());
                            list_textitem.addAll(textItems);
                            return true;
                        }else{
                            return false;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        tfv.loadError();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        tfv.finishLoad(s);
                    }
                });
        return s;
    }
    public void unsubscribe(){
        if(!s.isUnsubscribed())
            s.unsubscribe();
    }
}
