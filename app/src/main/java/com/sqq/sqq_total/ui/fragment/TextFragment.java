package com.sqq.sqq_total.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.sqq.sqq_total.viewholder.BaseViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class TextFragment extends BaseFragment implements TextPresenter.TextFmView{

    RecyclerView rv;
    TextView tv;
    //LoadingView lv;

    BaseAdapter adapter;
    TextPresenter tp;
    List<TextItem> list_textitem;

    private int resId[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3
            , R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8
            , R.drawable.img9, R.drawable.img10, R.drawable.img11
            , R.drawable.img12, R.drawable.img13, R.drawable.img14};
    private String des[] = {"云层里的阳光", "好美的海滩", "好美的海滩", "夕阳西下的美景", "夕阳西下的美景"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景", "好美的海滩","好美的海滩", "好美的海滩"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景"};

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tp = new TextPresenter(this);

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_text,container,false);
        tv = (TextView) rootView.findViewById(R.id.text_error);

        rv = (RecyclerView) rootView.findViewById(R.id.text_rv);
        rv.setLayoutManager(new LinearLayoutManager(getSelfActivity(), LinearLayout.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());

        initData();
    }

    @Override
    public void initData() {
        list_textitem = new ArrayList<>();

        /*lv = new LoadingView(getSelfActivity());
        lv.showDialog(getSelfActivity().getString(R.string.lv_tip));*/

        loadIngTextview();
        addSubscription(tp.loadItemData(true, list_textitem));

        /*lv.setLoadExitListener(new LoadingView.LoadExit() {
            @Override
            public void exit() {
                tp.unsubscribe();
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
                im.setImageResource(resId[position]);
            }

            @Override
            protected int getLayoutID() {
                return R.layout.textitem;
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intentTo(list_textitem.get(position).getTitle(), list_textitem.get(position).getUrl());
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
