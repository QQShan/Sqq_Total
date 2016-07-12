package com.sqq.sqq_total.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.sqq.sqq_total.presenter.TextPresenter;
import com.sqq.sqq_total.servicedata.TextItem;
import com.sqq.sqq_total.ui.activity.HeadlineActivity;
import com.sqq.sqq_total.utils.TimerUtils;
import com.sqq.sqq_total.view.LoadingView;
import com.sqq.sqq_total.view.pulltorefresh.OnLoadListener;
import com.sqq.sqq_total.view.pulltorefresh.SqqRecyclerview;
import com.sqq.sqq_total.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class TextFragment extends BaseFragment implements TextPresenter.TextFmView{

    SqqRecyclerview rv;
    TextView tv;
    //LoadingView lv;

    BaseAdapter adapter;
    TextPresenter tp;

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_text,container,false);
        tp = new TextPresenter(this);

        startGetData();
    }

    @Override
    public void startGetData() {

        /*lv = new LoadingView(getSelfActivity());
        lv.showDialog(getSelfActivity().getString(R.string.lv_tip));*/

        loadIngTextview();
        addSubscription(tp.loadItemData());

        /*lv.setLoadExitListener(new LoadingView.LoadExit() {
            @Override
            public void exit() {
                tp.unsubscribe();
            }
        });*/
    }

    @Override
    public void initViews(final List<TextItem> list_textitem) {
        tv = (TextView) rootView.findViewById(R.id.text_error);

        rv = (SqqRecyclerview) rootView.findViewById(R.id.sqqrv);
        rv.setLayoutManager(new LinearLayoutManager(getSelfActivity(), LinearLayout.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addSubscription(tp.loadItemData());
            }
        });
        rv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void OnLoadListener() {
                addSubscription(tp.loadMoreItemData());
            }
        });

        adapter = new BaseAdapter() {

            @Override
            public int getItemCount() {
                return list_textitem.size();
            }

            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                final TextView tv_title = holder.getView(R.id.ti_title);
                tv_title.setText(list_textitem.get(position).getTitle());
                final TextView tv_desc = holder.getView(R.id.ti_description);
                tv_desc.setText(list_textitem.get(position).getDescription());
                final TextView tv_time = holder.getView(R.id.ti_time);
                tv_time.setText(TimerUtils.getTimeUpToNow(list_textitem.get(position).getTime(),getSelfActivity()));

                ImageView im = holder.getView(R.id.ti_pic);
                Picasso.with(getSelfActivity()).load(list_textitem.get(position).getPicUrl()).into(im);
            }

            @Override
            protected int getLayoutID() {
                return R.layout.textitem;
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intentTo(list_textitem.get(position).getTitle(), list_textitem.get(position).getUrl()
                        , HeadlineActivity.class);
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
