package com.sqq.sqq_total.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.adapter.BaseAdapter;
import com.sqq.sqq_total.presenter.HeadlinePresenter;
import com.sqq.sqq_total.servicedata.HeadlineItem;
import com.sqq.sqq_total.ui.activity.HeadlineActivity;
import com.sqq.sqq_total.utils.TimerUtils;
import com.sqq.sqq_total.view.SlideView;
import com.sqq.sqq_total.viewholder.BaseViewHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class HeadlineFragment extends BaseFragment implements HeadlinePresenter.HeadlineView{

    //ViewPager vp;
    RecyclerView rv;
    BaseAdapter adapter;
    SlideView sv;
    static int mCurSlideView = 0;
    private PagerAdapter mPagerAdapter;
    HeadlinePresenter hPresenter;
    List<HeadlineItem> headlineItemsitems;

    private int resId[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3
            , R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8
            , R.drawable.img9, R.drawable.img10, R.drawable.img11
            , R.drawable.img12, R.drawable.img13, R.drawable.img14};
    private String des[] = {"云层里的阳光", "好美的海滩", "好美的海滩", "夕阳西下的美景", "夕阳西下的美景"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景", "好美的海滩","好美的海滩", "好美的海滩"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景"};

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_headline, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.headline_rv);
        rv.setLayoutManager(new LinearLayoutManager(getSelfActivity(), LinearLayout.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());

        //在这里发现一个问题，每次即使从磁盘缓存中读取内容依旧很慢,解决办法viewpager多缓存几页
        hPresenter = new HeadlinePresenter(this);
        headlineItemsitems = new ArrayList<>();
        addSubscription(hPresenter.loadData(true, headlineItemsitems));

        /*mPagerAdapter = new MyViewPageAdapter(getChildFragmentManager());*/
        mPagerAdapter = new MyViewPageAdapter();

        adapter = new BaseAdapter() {

            @Override
            public int getItemCount() {
                //加以是因为还有轮播图
                return headlineItemsitems.size()+1;
            }

            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                if(position==0) {
                    sv = holder.getView(R.id.headline_viewpager);
                    if (sv.getAdapter()==null)
                        sv.setAdapter(mPagerAdapter).startPlay();
                }else{
                    final TextView hl_title = holder.getView(R.id.hl_title);
                    hl_title.setText(headlineItemsitems.get(position-1).getTitle());
                    final TextView hl_description = holder.getView(R.id.hl_description);
                    hl_description.setText(headlineItemsitems.get(position-1).getDescription());
                    final TextView hl_time = holder.getView(R.id.hl_time);
                    hl_time.setText(TimerUtils.getTimeUpToNow(headlineItemsitems.get(position-1).getTime(),getSelfActivity()));

                    ImageView im = holder.getView(R.id.hl_pic);
                    im.setImageResource(resId[position-1]);
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
                if(getSelfActivity()!=null) {
                    Bundle bd= new Bundle();
                    bd.putString(BaseFragment.bundleURL,headlineItemsitems.get(position-1).getUrl());
                    bd.putString(BaseFragment.bundleTITLE,headlineItemsitems.get(position-1).getTitle());
                    goToWithInfo(HeadlineActivity.class, bd);
                }
                Log.d("sqqq", "点击了"+position);
            }
        });
        rv.setAdapter(adapter);

        //sv = (SlideView) rootView.findViewById(R.id.headline_viewpager);

        /*sv = new SlideView((Context)activityRef.get());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        sv.setLayoutParams(lp);
        mPagerAdapter = new MyViewPageAdapter(getChildFragmentManager());
        sv.setAdapter(mPagerAdapter).setCurrentItem(mCurSlideView).startPlay();*/

        /*
        这是其中一种添加头部的方式
        ImageView img = new ImageView((Context)activityRef.get());
        img.setImageResource(R.mipmap.ic_launcher);
        PlusRecyclerViewAdapter ps = new PlusRecyclerViewAdapter(adapter);
        ps.setHeaderView(img);
        ps.setFooterView(img);
        rv.setAdapter(ps);
        */
    }

    /*private class MyViewPageAdapter extends FragmentStatePagerAdapter {
        public MyViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return viewpagerFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

    }*/


    @Override
    public void intentTo() {

    }

    @Override
    public void refresh(boolean isRefreshing) {

    }

///////////////以下都是轮播图相关的代码
    private class MyViewPageAdapter extends PagerAdapter{
        private List<View> mListView;
        public MyViewPageAdapter(){
            mListView = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListView.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ViewGroup v= (ViewGroup)LayoutInflater.from(getSelfActivity()).inflate(R.layout.slideviewitem, null);
            v.setClickable(true);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("sqqq","pos"+position);
                }
            });
            ImageView img = (ImageView) v.findViewById(R.id.img);
            img.setImageResource(resId[position]);
            TextView tv = (TextView) v.findViewById(R.id.tv);
            tv.setText(des[position]);
            mListView.add(position,v);
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
