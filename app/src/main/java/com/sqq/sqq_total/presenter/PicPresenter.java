package com.sqq.sqq_total.presenter;

import android.content.Context;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.servicedata.PicItem;
import com.sqq.sqq_total.utils.NetWorkUtil;
import com.sqq.sqq_total.view.LoadingView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
            pfv.initData();
            hasNetwork = true;
        }else{
            //没有网络
            //Log.d("sqqq","没有网络");
            hasNetwork =false;
        }
    }
    public interface PicFmView{
        public void initData();
        public void initViews(List<PicItem> list);
        public void intentTo(String title,String url);
        public void getDataError(String info);
        public void hideErrorView();
        public void refresh(boolean isRefreshing);
    }

    PicFmView pfv;

    Subscription s;
    List<PicItem> picitem_list;

    public PicPresenter(PicFmView pfv){
        this.pfv = pfv;
        picitem_list = new ArrayList<>();
        NetWorkUtil.getInstance().addNetWorkListener(this);
    }

    /**
     * 如果清理会重新加载adapter
     * @param ifClear
     * @return
     */
    public Subscription loadItemData(final boolean ifClear){

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
                        if(ifClear){
                            picitem_list.clear();
                        }
                        picitem_list.addAll(picItems);
                        pfv.initViews(picitem_list);
                    }
                });
        return s;
    }

    public void unsubscribe(){
        if(!s.isUnsubscribed())
            s.unsubscribe();
    }
}
