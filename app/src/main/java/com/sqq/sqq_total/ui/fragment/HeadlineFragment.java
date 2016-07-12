package com.sqq.sqq_total.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
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
import com.sqq.sqq_total.presenter.HeadlinePresenter;
import com.sqq.sqq_total.servicedata.HeadlineItem;
import com.sqq.sqq_total.servicedata.SlideviewItem;
import com.sqq.sqq_total.ui.activity.HeadlineActivity;
import com.sqq.sqq_total.utils.TimerUtils;
import com.sqq.sqq_total.utils.TranslateUtils;
import com.sqq.sqq_total.view.SlideView;
import com.sqq.sqq_total.view.pulltorefresh.OnLoadListener;
import com.sqq.sqq_total.view.pulltorefresh.SqqRecyclerview;
import com.sqq.sqq_total.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sqq on 2016/5/30.
 */
public class HeadlineFragment extends BaseFragment implements HeadlinePresenter.HeadlineView{

    //ViewPager vp;
    /*RecyclerView rv;*/
    SqqRecyclerview sqqrv;
    TextView tv;
    BaseAdapter adapter;
    SlideView sv;
    //LoadingView lv;

    static int mCurSlideView = 0;
    private PagerAdapter mPagerAdapter;
    HeadlinePresenter hPresenter;
    List<View> viewpagerViews;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_headline, container, false);

        //在这里发现一个问题，每次即使从磁盘缓存中读取内容依旧很慢,解决办法viewpager多缓存几页
        hPresenter = new HeadlinePresenter(this);

        /*swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.sf);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setColorSchemeResources(DEFAULT_COLOR_RES);
        int start = getSelfActivity().getResources().getDimensionPixelSize(R.dimen.title_height);
        swipeRefreshLayout.setProgressViewOffset(false,start,100);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hPresenter.loadItemData(true);
            }
        });

        rv = (RecyclerView) rootView.findViewById(R.id.headline_rv);
        rv.setLayoutManager(new LinearLayoutManager(getSelfActivity(), LinearLayout.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());*/
        sqqrv = (SqqRecyclerview) rootView.findViewById(R.id.sqqrv);
        sqqrv.setLayoutManager(new LinearLayoutManager(getSelfActivity(), LinearLayout.VERTICAL, false));
        sqqrv.setItemAnimator(new DefaultItemAnimator());

        sqqrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addSubscription(hPresenter.loadItemData());
            }
        });
        sqqrv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void OnLoadListener() {
                addSubscription(hPresenter.loadMoreItemData());
            }
        });

        tv = (TextView) rootView.findViewById(R.id.headline_error);

        initData();
    }

    @Override
    public void initData() {
        Log.d("sqqq", "initData");

        viewpagerViews = new ArrayList<>();

        /*lv = new LoadingView(getSelfActivity());
        lv.showDialog(getSelfActivity().getString(R.string.lv_tip));*/
        loadIngTextview();
        addSubscription(hPresenter.loadItemData());
       /* lv.setLoadExitListener(new LoadingView.LoadExit() {
            @Override
            public void exit() {
                hPresenter.unsubscribe();
            }
        });*/
    }

    @Override
    public void initViews(final List<HeadlineItem> headlineItemsitems,List<SlideviewItem> slideviewItemsitems) {
        //加载完数据
        //lv.dismissDialog();
        loadTextviewEnd();

        viewpagerViews = hPresenter.initSlideView(getSelfActivity());
        adapter = new BaseAdapter() {

            @Override
            public int getItemCount() {
                //加1是因为还有轮播图
                return headlineItemsitems.size()+1;
            }

            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                if(position==0) {
                    sv = holder.getView(R.id.headline_viewpager);
                    if (sv.getAdapter()==null) {
                        mPagerAdapter = new MyViewPageAdapter();
                        sv.setAdapter(mPagerAdapter).startPlay();
                    }
                }else{
                    final TextView hl_title = holder.getView(R.id.hl_title);
                    hl_title.setText(headlineItemsitems.get(position-1).getTitle());
                    final TextView hl_description = holder.getView(R.id.hl_description);
                    hl_description.setText(headlineItemsitems.get(position-1).getDescription());
                    final TextView hl_time = holder.getView(R.id.hl_time);
                    hl_time.setText(TimerUtils.getTimeUpToNow(headlineItemsitems.get(position-1).getTime(),getSelfActivity()));

                    //图片加载的话先使用picasso
                    ImageView im = holder.getView(R.id.hl_pic);
                    Picasso.with(getSelfActivity()).load(headlineItemsitems.get(position-1).getPicUrl()).into(im);
                }
            }

            @Override
            public int getItemViewType(int position) {
                if(position==0){
                    return BaseAdapter.TYPE_VIEWPAGER;
                }
                return super.getItemViewType(position);
            }

            @Override
            protected int getLayoutID() {
                return R.layout.headlineitem;
            }

            @Override
            public int getViewpagerLayoutID() {
                return R.layout.slideviewxml;
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("sqqq", "点击了" + position);
                if (getSelfActivity() != null && position != 0) {
                    intentTo(headlineItemsitems.get(position - 1).getTitle(), headlineItemsitems.get(position - 1).getUrl()
                            , HeadlineActivity.class);
                }
            }
        });
        sqqrv.setAdapter(adapter);
    }

    @Override
    public void intentTo(String title,String url) {
        intentTo(title, url
                , HeadlineActivity.class);
    }

    @Override
    public void getDataError(String info) {
        tv.setTextColor(getResources().getColor(R.color.red));
        tv.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        tv.setText(R.string.network_error);
        tv.setVisibility(View.VISIBLE);

        /*tv.setText(info);*/
    }

    @Override
    public void hideErrorView() {
        tv.setVisibility(View.GONE);
    }

    @Override
    public void refresh(boolean isRefreshing) {
        adapter.notifyDataSetChanged();
        sqqrv.setRefreshing(isRefreshing);
    }

    @Override
    public void finishLoad(boolean hasData) {
        Log.d("sqqq", "finishLoad");
        if(hasData){
            adapter.notifyDataSetChanged();
        }
        sqqrv.endLoadRefresh();
    }

    @Override
    public void loadError() {
        sqqrv.loadError();
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

    ///////////////以下都是轮播图相关的代码
    private class MyViewPageAdapter extends PagerAdapter{
        public MyViewPageAdapter(){
        }

        @Override
        public int getCount() {
            return viewpagerViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewpagerViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View v = viewpagerViews.get(position);
            container.addView(v,0);
            return v;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(sv!=null)
            mCurSlideView = sv.getCurrentItem();
    }
}
