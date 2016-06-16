package com.sqq.sqq_total.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.utils.TranslateUtils;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/5/30.
 */
public class LeftFragment extends BaseFragment implements View.OnClickListener{

    private PagerAdapter mPagerAdapter;
    private static int mCurPage = 0;
    TextView tv_headline,tv_pic,tv_text,tv_video;

    ViewPager vp;

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_left, container, false);
        tv_headline = (TextView) rootView.findViewById(R.id.headline);
        tv_headline.setOnClickListener(this);
        tv_text = (TextView) rootView.findViewById(R.id.text);
        tv_text.setOnClickListener(this);
        tv_pic = (TextView) rootView.findViewById(R.id.pic);
        tv_pic.setOnClickListener(this);
        tv_video = (TextView) rootView.findViewById(R.id.video);
        tv_video.setOnClickListener(this);

        vp = (ViewPager) rootView.findViewById(R.id.left_viewpager);
        //前后缓存三页
        vp.setOffscreenPageLimit(3);
        mPagerAdapter = new MyViewPageAdapter(getChildFragmentManager());
        vp.setAdapter(mPagerAdapter);
        Log.d("sqqOn", mCurPage + "");
        vp.setCurrentItem(mCurPage);
        changeTitle(mCurPage);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurPage = position;
                Log.d("sqqF",""+mCurPage);
                changeTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.headline:
                vp.setCurrentItem(0);
                break;
            case R.id.text:
                vp.setCurrentItem(1);
                break;
            case R.id.pic:
                vp.setCurrentItem(2);
                break;
            case R.id.video:
                vp.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    private class MyViewPageAdapter extends FragmentStatePagerAdapter {
        public MyViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new HeadlineFragment();
            }else if(position==1){
                return new TextFragment();
            }else if(position==2){
                return new PicFragment();
            }else if(position==3){
                return new VideoFragment();
            }else{
                return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("sqq",mCurPage+"");
    }

    public void changeTitle(int pnumber){

        float size = TranslateUtils.sp2px(20, (Context)activityRef.get());
        tv_headline.setTextSize(size);
        tv_pic.setTextSize(size);
        tv_text.setTextSize(size);
        tv_video.setTextSize(size);
        tv_headline.setTextColor(getResources().getColor(R.color.colorWhite));
        tv_pic.setTextColor(getResources().getColor(R.color.colorWhite));
        tv_text.setTextColor(getResources().getColor(R.color.colorWhite));
        tv_video.setTextColor(getResources().getColor(R.color.colorWhite));

        switch(pnumber){
            case 0:
                tv_headline.setTextSize(TranslateUtils.sp2px(25, (Context)activityRef.get()));
                tv_headline.setTextColor(getResources().getColor(R.color.colorTitleText));
                break;
            case 1:
                tv_text.setTextSize(TranslateUtils.sp2px(25, (Context)activityRef.get()));
                tv_text.setTextColor(getResources().getColor(R.color.colorTitleText));
                break;
            case 2:
                tv_pic.setTextSize(TranslateUtils.sp2px(25, (Context)activityRef.get()));
                tv_pic.setTextColor(getResources().getColor(R.color.colorTitleText));
                break;
            case 3:
                tv_video.setTextSize(TranslateUtils.sp2px(25, (Context)activityRef.get()));
                tv_video.setTextColor(getResources().getColor(R.color.colorTitleText));
                break;
            default:
                break;
        }
    }
}
