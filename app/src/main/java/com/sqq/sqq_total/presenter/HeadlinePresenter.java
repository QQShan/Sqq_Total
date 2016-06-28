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

import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

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
    }

    private int resId[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3
            , R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8
            , R.drawable.img9, R.drawable.img10, R.drawable.img11
            , R.drawable.img12, R.drawable.img13, R.drawable.img14};

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
     * @param ifClear
     * @return
     */
    public Subscription loadItemData(final boolean ifClear){

        s = Observable
                .zip(App.getRetrofitInstance().getApiService()
                                .getLatestItemInfo(AppConfig.headlineItemCount),
                        App.getRetrofitInstance().getApiService()
                                .getLatestSlideviewInfo(AppConfig.slideviewItemCount),
                        new Func2<List<HeadlineItem>, List<SlideviewItem>, Void>() {
                            @Override
                            public Void call(List<HeadlineItem> headlineItems, List<SlideviewItem> slideviewItems) {
                                if (ifClear) {
                                    Log.d("sqqq",headlineItemsitems.size()+"");
                                    headlineItemsitems.clear();
                                    slideviewItemsitems.clear();
                                }

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
                        if(ifClear){
                            view.initViews(headlineItemsitems,slideviewItemsitems);
                        }

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
                    img.setImageResource(resId[slideviewItemsitems.size()-1]);

                    TextView tv = (TextView) v.findViewById(R.id.tv);
                    tv.setText(slideviewItemsitems.get(slideviewItemsitems.size()-1).getTitle());
                }else{
                    ImageView img = (ImageView) v.findViewById(R.id.img);
                    img.setImageResource(resId[0]);

                    TextView tv = (TextView) v.findViewById(R.id.tv);
                    tv.setText(slideviewItemsitems.get(0).getTitle());
                }
            }else{
                ImageView img = (ImageView) v.findViewById(R.id.img);
                img.setImageResource(resId[s]);

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
    public void unsubscribe(){
        if(!s.isUnsubscribed())
            s.unsubscribe();
    }
}
