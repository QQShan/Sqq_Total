package com.sqq.sqq_total.presenter;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.servicedata.PicCommetItem;
import com.sqq.sqq_total.servicedata.VideoCommentItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sqq on 2016/7/21.
 */
public class VideoActivityPresentr {

    public interface VideoPlayerView{
        public void showStatusBar();
        public void hideStatusBar();

        public void startGetData();
        public void initViews(List<VideoCommentItem> list);
        public void getDataError(String info);
        public void refresh(boolean isRefreshing);
        public void finishLoad(boolean hasData);
        public void loadError();
        public void publishSuccess();
        public void publishError();
    }

    VideoPlayerView view;
    long videoId;
    List<VideoCommentItem> list_videoComment;

    public VideoActivityPresentr(VideoPlayerView view,long videoId){
        this.view = view;
        this.videoId = videoId;
        list_videoComment = new ArrayList<>();
        view.initViews(list_videoComment);
    }

    public void initSystembar(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0实现全透明
            Window window = act.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            view.showStatusBar();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4全透明
            act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            view.showStatusBar();
        } else {
            view.hideStatusBar();
        }
    }

    /**
     * 加载最新数据
     * @return
     */
    public Subscription loadItemData(){
        Subscription s = App.getRetrofitInstance().getApiService()
                .getLatestVideoCommentItemInfo(AppConfig.videoCommentCount,videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<VideoCommentItem>>() {
                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        view.getDataError(throwable.toString());
                    }

                    @Override
                    public void onNext(List<VideoCommentItem> items) {
                        list_videoComment.clear();
                        list_videoComment.addAll(items);
                    }
                });
        return s;
    }

    /**
     * 加载更多
     */
    public Subscription loadMoreItemData(){
        Log.d("sqqerror",list_videoComment.get(list_videoComment.size() - 1).getId()+":"+videoId);

        Subscription s =App.getRetrofitInstance().getApiService()
                .getVideoCommentItemInfo(AppConfig.videoCommentCount,
                        videoId,
                        list_videoComment.get(list_videoComment.size() - 1).getId()
                        )
                .map(new Func1<List<VideoCommentItem>, Boolean>() {
                    @Override
                    public Boolean call(List<VideoCommentItem> videoCommetItems) {
                        if (videoCommetItems != null && videoCommetItems.size() != 0) {
                            list_videoComment.addAll(videoCommetItems);
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
                        Log.d("sqqerror",throwable.toString());
                        view.loadError();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        view.finishLoad(aBoolean);
                    }
                });
        return s;
    }

    /**
     * 发布评论
     */
    public Subscription publishComment(String comment){
        //long userId = new Long(null);
        Subscription s =App.getRetrofitInstance().getApiService()
                .publishVideoComment(RequestBody.create(null, videoId + "")
                        , RequestBody.create(null, "")
                        , RequestBody.create(null, comment))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //发表失败
                        view.publishError();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        //发表成功
                        view.publishSuccess();
                    }
                });
        return s;
    }
}
