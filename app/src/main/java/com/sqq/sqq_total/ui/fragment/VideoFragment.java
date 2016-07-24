package com.sqq.sqq_total.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.adapter.BaseAdapter;
import com.sqq.sqq_total.presenter.VideoPresenter;
import com.sqq.sqq_total.servicedata.VideoItem;
import com.sqq.sqq_total.ui.activity.VideoActivity;
import com.sqq.sqq_total.utils.NumberUtils;
import com.sqq.sqq_total.utils.TimerUtils;
import com.sqq.sqq_total.view.pulltorefresh.OnLoadListener;
import com.sqq.sqq_total.view.pulltorefresh.SqqRecyclerview;
import com.sqq.sqq_total.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sqq on 2016/5/30.
 */
public class VideoFragment extends BaseFragment implements VideoPresenter.VideoFmView{

    SqqRecyclerview rv;
    TextView tv;
    //LoadingView lv;

    BaseAdapter adapter;
    VideoPresenter vp;

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_video,container,false);
        vp = new VideoPresenter(this);

        startGetData();
    }

    @Override
    public void startGetData() {

        /*lv = new LoadingView(getSelfActivity());
        lv.showDialog(getSelfActivity().getString(R.string.lv_tip));*/

        loadIngTextview();
        addSubscription(vp.loadItemData(true));

        /*lv.setLoadExitListener(new LoadingView.LoadExit() {
            @Override
            public void exit() {
                vp.unsubscribe();
            }
        });*/
    }

    @Override
    public void initViews(final List<VideoItem> videoitem_list) {
        tv = (TextView) rootView.findViewById(R.id.video_error);

        rv = (SqqRecyclerview) rootView.findViewById(R.id.sqqrv);
        rv.setLayoutManager(new GridLayoutManager(getSelfActivity(), 2));
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addSubscription(vp.loadItemData(true));
            }
        });
        rv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void OnLoadListener() {
                addSubscription(vp.loadMoreItemData());
            }
        });

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
                tv1.setText(TimerUtils.getTimeUpToNow(videoitem_list.get(position).getTime(), getSelfActivity()));

                TextView tv2 = holder.getView(R.id.videoitem_counts);
                tv2.setText(NumberUtils.getVideoWatchNumber(getSelfActivity(), videoitem_list.get(position)
                        .getNumber()));

                ImageView im = holder.getView(R.id.videoitem_pic);
                Picasso.with(getSelfActivity()).load(videoitem_list.get(position).getPicUrl()).into(im);

            }

            @Override
            protected int getLayoutID() {
                return R.layout.videoitem;
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intentTo(videoitem_list.get(position).getDescription(),
                        videoitem_list.get(position).getVideoUrl(),
                        videoitem_list.get(position).getId(), VideoActivity.class);
            }
        });

        rv.setAdapter(adapter);
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
        //lv.dismissDialog();
        loadTextviewEnd();

        adapter.notifyDataSetChanged();
        rv.setRefreshing(isRefreshing);
    }

    @Override
    public void finishLoad(boolean hasData) {
        if(hasData){
            adapter.notifyDataSetChanged();
        }
        rv.endLoadRefresh();
    }

    @Override
    public void loadError() {
        rv.loadError();
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
