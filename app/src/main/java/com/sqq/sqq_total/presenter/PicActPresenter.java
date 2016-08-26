package com.sqq.sqq_total.presenter;

import android.content.Context;
import android.util.Log;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.servicedata.PicCommetItem;
import com.sqq.sqq_total.servicedata.PicItem;
import com.sqq.sqq_total.servicedata.TextItem;
import com.sqq.sqq_total.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sqq on 2016/7/15.
 */
public class PicActPresenter {

    public interface PicActView{
        public void startGetData();
        public void initViews(List<PicCommetItem> list);
        public void getDataError(String info);
        public void hideErrorView();
        public void refresh(boolean isRefreshing);
        public void finishLoad(boolean hasData);
        public void loadError();
        public void publishSuccess();
        public void publishError();
    }

    PicActView pav;
    List<PicCommetItem> list_picComment;

    long picId;

    public PicActPresenter(PicActView pav,long picId){
        this.pav = pav;
        this.picId = picId;
        list_picComment = new ArrayList<>();
        pav.initViews(list_picComment);
    }

    /**
     * 加载最新数据
     * @return
     */
    public Subscription loadItemData(){
        Subscription s = App.getRetrofitInstance().getApiService()
                .getLatestPicCommentInfo(AppConfig.picCommentCount,picId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PicCommetItem>>() {
                    @Override
                    public void onCompleted() {
                        pav.refresh(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        pav.getDataError(throwable.toString());
                    }

                    @Override
                    public void onNext(List<PicCommetItem> items) {
                        list_picComment.clear();
                        list_picComment.addAll(items);
                    }
                });
        return s;
    }

    /**
     * 加载更多
     */
    public Subscription loadMoreItemData(){
        Subscription s =App.getRetrofitInstance().getApiService()
                .getPicCommentInfo(AppConfig.picCommentCount,
                        picId,
                        list_picComment.get(list_picComment.size() - 1).getId()
                        )
                .map(new Func1<List<PicCommetItem>, Boolean>() {
                    @Override
                    public Boolean call(List<PicCommetItem> picCommetItems) {
                        if (picCommetItems != null && picCommetItems.size() != 0) {
                            list_picComment.addAll(picCommetItems);
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
                        pav.loadError();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        pav.finishLoad(aBoolean);
                    }
                });
        return s;
    }

    /**
     * 发布评论
     */
    public Subscription publishComment(String comment,Context con){
        String userId = ""+ PreferenceUtils.getLong(con, R.string.prefer_userId, -1L);

        Log.d("sqqqq","userId"+userId+" comment"+comment+" picId"+picId);
        Subscription s =App.getRetrofitInstance().getApiService()
                .publishComment(RequestBody.create(null, picId + "")
                        , RequestBody.create(null, userId)
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
                        pav.publishError();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        //发表成功
                        pav.publishSuccess();
                    }
                });
        return s;
    }
}
