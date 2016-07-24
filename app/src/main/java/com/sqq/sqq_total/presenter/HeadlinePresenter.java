package com.sqq.sqq_total.presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.AppConfig;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.servicedata.HeadlineItem;
import com.sqq.sqq_total.servicedata.SlideviewItem;
import com.sqq.sqq_total.utils.NetWorkUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by sqq on 2016/6/16.
 */
public class HeadlinePresenter implements NetWorkUtil.NetworkListener {
    private boolean hasNetwork = true;
    @Override
    public void onConnected(boolean collect) {
        if(collect && !hasNetwork){
            //Log.d("sqqq","有网络,初始化数据");
            view.hideErrorView();
            view.initData();
            hasNetwork = true;
        }else{
            //没有网络
            //Log.d("sqqq","没有网络");
            hasNetwork =false;
        }
    }

    public interface HeadlineView{
        public void initData();
        public void initViews(List<HeadlineItem> headlineItemsitems,List<SlideviewItem> slideviewItemsitems);
        public void intentTo(String title,String url);
        public void getDataError(String info);
        public void hideErrorView();
        public void refresh(boolean isRefreshing);
        public void finishLoad(boolean hasData);
        public void loadError();
    }

    Subscription s;
    public HeadlineView view;

    List<HeadlineItem> headlineItemsitems;
    List<SlideviewItem> slideviewItemsitems;


    public HeadlinePresenter(HeadlineView view){
        this.view = view;

        headlineItemsitems = new ArrayList<>();
        slideviewItemsitems = new ArrayList<>();

        NetWorkUtil.getInstance().addNetWorkListener(this);
    }

    /**
     * 如果清理会重新加载adapter
     * 加载轮播图在内的数据
     * @return
     */
    public Subscription loadItemData(){
        s = Observable
                .zip(App.getRetrofitInstance().getApiService()
                                .getLatestItemInfo(AppConfig.headlineItemCount),
                        App.getRetrofitInstance().getApiService()
                                .getLatestSlideviewInfo(AppConfig.slideviewItemCount),
                        new Func2<List<HeadlineItem>, List<SlideviewItem>, Void>() {
                            @Override
                            public Void call(List<HeadlineItem> headlineItems, List<SlideviewItem> slideviewItems) {
                                headlineItemsitems.clear();
                                slideviewItemsitems.clear();

                                headlineItemsitems.addAll(headlineItems);
                                slideviewItemsitems.addAll(slideviewItems);
                                return null;
                            }
                        })
                /*.map(new Func1<Void, Void>() {
                    @Override
                    public Void call(Void aVoid) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        view.refresh(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //没有网络，但是缓存中有数据就不会调用这里
                        Log.d("sqqq", throwable.toString());
                        view.getDataError(throwable.toString());
                    }

                    @Override
                    public void onNext(Void s) {
                        view.initViews(headlineItemsitems,slideviewItemsitems);
                    }
                });
        return s;
    }

    public List<View> initSlideView(Context context){
        List<View> lv = new ArrayList<>();
        for(int i=0;i<slideviewItemsitems.size()+2;i++) {
            final int s =i-1;
            ViewGroup v = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.slideviewitem, null);
            v.setClickable(true);
            if(s==-1 || s == slideviewItemsitems.size()){
                //首尾各一条,为了无限滑动的效果
                if(s==-1){
                    ImageView img = (ImageView) v.findViewById(R.id.img);
                    Picasso.with(context).load(slideviewItemsitems.get(slideviewItemsitems.size()-1).getPicUrl()).into(img);

                    TextView tv = (TextView) v.findViewById(R.id.tv);
                    tv.setText(slideviewItemsitems.get(slideviewItemsitems.size()-1).getTitle());
                }else{
                    ImageView img = (ImageView) v.findViewById(R.id.img);
                    Picasso.with(context).load(slideviewItemsitems.get(0).getPicUrl()).into(img);

                    TextView tv = (TextView) v.findViewById(R.id.tv);
                    tv.setText(slideviewItemsitems.get(0).getTitle());
                }
            }else{
                ImageView img = (ImageView) v.findViewById(R.id.img);
                Picasso.with(context).load(slideviewItemsitems.get(s).getPicUrl()).into(img);

                TextView tv = (TextView) v.findViewById(R.id.tv);
                tv.setText(slideviewItemsitems.get(s).getTitle());
            }
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("sqqq","pos"+s+"url:"+slideviewItemsitems.get(s).getUrl());
                    view.intentTo(slideviewItemsitems.get(s).getTitle(),slideviewItemsitems.get(s).getUrl());
                }
            });
            lv.add(v);
        }
        return lv;
    }

    /**
     * 加载更多
     */
    public Subscription loadMoreItemData(){
        s = App.getRetrofitInstance().getApiService()
                .getItemInfo(AppConfig.headlineItemCount,headlineItemsitems.get(headlineItemsitems.size()-1).getId())
                .map(new Func1<List<HeadlineItem>, Boolean>() {

                    @Override
                    public Boolean call(List<HeadlineItem> headlineItems) {
                        if(headlineItems!=null&&headlineItems.size()!=0){
                            Log.d("sqqq", "loadmore" + headlineItems.size());
                            headlineItemsitems.addAll(headlineItems);
                            return true;
                        }else{
                            return false;
                        }
                    }
                })
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aVoid) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return aVoid;
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
                        //没有网络，但是缓存中有数据就不会调用这里
                        Log.d("sqqq", throwable.toString());
                        view.loadError();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        view.finishLoad(s);
                        //view.initViews(headlineItemsitems, slideviewItemsitems);
                    }
                });
        return s;
    }

    public void unsubscribe(){
        if(!s.isUnsubscribed())
            s.unsubscribe();
    }
}
