package com.sqq.sqq_total.presenter;

import android.content.Context;
import android.util.Log;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.servicedata.PicItem;
import com.sqq.sqq_total.servicedata.TextItem;
import com.sqq.sqq_total.servicedata.VideoItem;
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
public class VideoPresenter implements NetWorkUtil.NetworkListener {
    private boolean hasNetwork = true;
    @Override
    public void onConnected(boolean collect) {
        if(collect && !hasNetwork){
            //Log.d("sqqq","有网络,初始化数据");
            vfv.hideErrorView();
            vfv.startGetData();
            hasNetwork = true;
        }else{
            //没有网络
            //Log.d("sqqq","没有网络");
            hasNetwork =false;
        }
    }
    public interface VideoFmView{
        public void startGetData();
        public void initViews(List<VideoItem> videoitem_list);
        public void getDataError(String info);
        public void hideErrorView();
        public void refresh(boolean isRefreshing);
        public void finishLoad(boolean hasData);
        public void loadError();
    }

    VideoFmView vfv;
    Subscription s;
    List<VideoItem> videoitem_list;

    public VideoPresenter(VideoFmView vfv){
        this.vfv = vfv;
        videoitem_list = new ArrayList<>();
        NetWorkUtil.getInstance().addNetWorkListener(this);
        vfv.initViews(videoitem_list);
    }

    /**
     * 如果清理会重新加载adapter
     * @param ifClear
     * @return
     */
    public Subscription loadItemData(final boolean ifClear){

        s = App.getRetrofitInstance().getApiService()
                .getLatestVideoItemInfo(AppConfig.videoItemCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<VideoItem>>() {
                    @Override
                    public void onCompleted() {
                        vfv.refresh(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        vfv.getDataError(throwable.toString());
                    }

                    @Override
                    public void onNext(List<VideoItem> textItems) {
                        videoitem_list.clear();
                        videoitem_list.addAll(textItems);
                    }
                });
        return s;
    }

    /**
     * 加载更多
     */
    public Subscription loadMoreItemData(){
        s = App.getRetrofitInstance().getApiService()
                .getVideoItemInfo(AppConfig.textItemCount, videoitem_list.get(videoitem_list.size() - 1).getId())
                .map(new Func1<List<VideoItem>, Boolean>() {
                    @Override
                    public Boolean call(List<VideoItem> videoItems) {
                        if (videoItems != null && videoItems.size() != 0) {
                            Log.d("sqqq", "loadmore" + videoItems.size());
                            videoitem_list.addAll(videoItems);
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
                        vfv.loadError();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        vfv.finishLoad(s);
                    }
                });
        return s;
    }

    public void unsubscribe(){
        if(!s.isUnsubscribed())
            s.unsubscribe();
    }
}
