package com.sqq.sqq_total.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.adapter.BaseAdapter;
import com.sqq.sqq_total.presenter.VideoPresenter;
import com.sqq.sqq_total.servicedata.VideoItem;
import com.sqq.sqq_total.ui.activity.HeadlineActivity;
import com.sqq.sqq_total.utils.NumberUtils;
import com.sqq.sqq_total.utils.TimerUtils;
import com.sqq.sqq_total.view.LoadingView;
import com.sqq.sqq_total.view.SlideView;
import com.sqq.sqq_total.viewholder.BaseViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sqq on 2016/5/30.
 */
public class VideoFragment extends BaseFragment implements VideoPresenter.VideoFmView{

    RecyclerView rv;
    TextView tv;
    //LoadingView lv;

    BaseAdapter adapter;
    VideoPresenter vp;
    List<VideoItem> videoitem_list;

    private int resId[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3
            , R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8
            , R.drawable.img9, R.drawable.img10, R.drawable.img11
            , R.drawable.img12, R.drawable.img13, R.drawable.img14};

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vp = new VideoPresenter(this);
        
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_video,container,false);

        tv = (TextView) rootView.findViewById(R.id.video_error);

        rv = (RecyclerView) rootView.findViewById(R.id.video_rv);
        rv.setLayoutManager(new GridLayoutManager(getSelfActivity(), 2));
        rv.setItemAnimator(new DefaultItemAnimator());

        initData();
    }

    @Override
    public void initData() {
        videoitem_list = new ArrayList<>();
        /*lv = new LoadingView(getSelfActivity());
        lv.showDialog(getSelfActivity().getString(R.string.lv_tip));*/

        loadIngTextview();
        addSubscription(vp.loadItemData(true, videoitem_list));

        /*lv.setLoadExitListener(new LoadingView.LoadExit() {
            @Override
            public void exit() {
                vp.unsubscribe();
            }
        });*/
    }

    @Override
    public void initViews() {
        //lv.dismissDialog();
        loadTextviewEnd();
        adapter = new BaseAdapter() {

            @Override
            public int getItemCount() {
                return videoitem_list.size();
            }

            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                final TextView tv = holder.getView(R.id.videoitem_desc);
                tv.setText(videoitem_list.get(position).getDescription());
                TextView tv1 = holder.getView(R.id.videoitem_time);
                tv1.setText(TimerUtils.getTimeUpToNow(videoitem_list.get(position).getTime(),getSelfActivity()));

                TextView tv2 = holder.getView(R.id.videoitem_counts);
                tv2.setText(NumberUtils.getVideoWatchNumber(getSelfActivity(),videoitem_list.get(position)
                .getNumber()));

                ImageView im = holder.getView(R.id.videoitem_pic);
                im.setImageResource(resId[position]);

            }

            @Override
            protected int getLayoutID() {
                return R.layout.videoitem;
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intentTo(videoitem_list.get(position).getDescription(), videoitem_list.get(position).getUrl());
            }
        });

        rv.setAdapter(adapter);
    }

    @Override
    public void intentTo(String title, String url) {
        Bundle bd= new Bundle();
        bd.putString(BaseFragment.bundleURL,url);
        bd.putString(BaseFragment.bundleTITLE,title);
        goToWithInfo(HeadlineActivity.class, bd);
    }

    @Override
    public void getDataError(String info) {
        tv.setTextColor(getResources().getColor(R.color.red));
        tv.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        tv.setText(R.string.network_error);
        tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorView() {
        tv.setVisibility(View.GONE);
    }

    @Override
    public void refresh(boolean isRefreshing) {

    }
    private void loadIngTextview(){
        tv.setTextColor(getResources().getColor(R.color.colorWhite));
        tv.setBackgroundColor(getResources().getColor(R.color.colorGray));
        tv.setText(R.string.lv_tip);
        tv.setVisibility(View.VISIBLE);
    }

    private void loadTextviewEnd(){
        tv.setVisibility(View.GONE);
    }
}
