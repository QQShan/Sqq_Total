package com.sqq.sqq_total.presenter;

import android.content.Context;
import android.util.Log;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.servicedata.PicItem;
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
public class PicPresenter implements NetWorkUtil.NetworkListener {
    private boolean hasNetwork = true;
    @Override
    public void onConnected(boolean collect) {
        if(collect && !hasNetwork){
            //Log.d("sqqq","有网络,初始化数据");
            pfv.hideErrorView();
            pfv.startGetData();
            hasNetwork = true;
        }else{
            //没有网络
            //Log.d("sqqq","没有网络");
            hasNetwork =false;
        }
    }
    public interface PicFmView{
        public void initViews(List<PicItem> list);
        public void startGetData();
        public void getDataError(String info);
        public void hideErrorView();
        public void refresh(boolean isRefreshing);
        public void finishLoad(boolean hasData);
        public void loadError();
    }

    PicFmView pfv;

    Subscription s;
    List<PicItem> picitem_list;

    public PicPresenter(PicFmView pfv){
        this.pfv = pfv;
        picitem_list = new ArrayList<>();
        NetWorkUtil.getInstance().addNetWorkListener(this);
        pfv.initViews(picitem_list);
    }

    /**
     * 如果清理会重新加载adapter
     * @return
     */
    public Subscription loadItemData(){

        s = App.getRetrofitInstance().getApiService()
                .getLatestPicItemInfo(AppConfig.picItemCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PicItem>>() {
                    @Override
                    public void onCompleted() {
                        pfv.refresh(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        pfv.getDataError(throwable.toString());
                    }

                    @Override
                    public void onNext(List<PicItem> picItems) {
                        picitem_list.clear();
                        picitem_list.addAll(picItems);
                    }
                });
        return s;
    }

    /**
     * 加载更多
     */
    public Subscription loadMoreItemData(){
        s = App.getRetrofitInstance().getApiService()
                .getPicItemInfo(AppConfig.textItemCount, picitem_list.get(picitem_list.size() - 1).getId())
                .map(new Func1<List<PicItem>, Boolean>() {
                    @Override
                    public Boolean call(List<PicItem> picItems) {
                        if (picItems != null && picItems.size() != 0) {
                            Log.d("sqqq", "loadmore" + picItems.size());
                            picitem_list.addAll(picItems);
                            return true;
                        } else {
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
                        pfv.loadError();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        pfv.finishLoad(s);
                    }
                });
        return s;
    }

    public void unsubscribe(){
        if(!s.isUnsubscribed())
            s.unsubscribe();
    }
}
