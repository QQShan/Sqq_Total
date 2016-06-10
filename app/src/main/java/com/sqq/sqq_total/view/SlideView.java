package com.sqq.sqq_total.view;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.utils.TranslateUtils;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/6/1.
 */
public class SlideView extends FrameLayout implements ViewPager.OnPageChangeListener{

    //播放延迟
    private int delay = 1000;
    private long recentSlideTime;
    private boolean showingState;
    Context con;
    ViewPager vp;
    LinearLayout ll;
    private int mCurrentItem;
    Timer timer;

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        con = context;
        ViewGroup vg = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.slideview, this, true);
        vp = (ViewPager) vg.findViewById(R.id.slide_viewpager);
        vp.addOnPageChangeListener(this);
        ll = (LinearLayout) vg.findViewById(R.id.slide_circles);
        showingState = true;
    }

    public SlideView setAdapter(PagerAdapter adapter){
        vp.setAdapter(adapter);
        initcCircle(adapter);
        return this;
    }

    public SlideView setAdapter(FragmentStatePagerAdapter adapter){
        vp.setAdapter(adapter);
        initcCircle(adapter);
        return this;
    }

    public PagerAdapter getAdapter(){
        return vp.getAdapter();
    }

    public SlideView setDelay(int timeMs){
        delay = timeMs;
        return this;
    }

    public void startPlay(){
       if(timer!=null){
           timer.cancel();
       }
        timer = new Timer();
        timer.schedule(new WeakTimerTask(this), delay, delay);
    }

    private void stopPlay(){
        if (timer!=null){
            timer.cancel();
            timer = null;
        }
    }

    private void initcCircle(PagerAdapter pa){

        int pad = TranslateUtils.dp2px(2,con);
        for (int i=0;i<pa.getCount();i++){
            ImageView v = new ImageView(con);
            v.setImageResource(R.drawable.circle_u);
            v.setPadding(pad,pad,pad,pad);
            ll.addView(v);
        }
    }

    public SlideView setCurrentItem(int position){
        vp.setCurrentItem(position);
        setCircleRes(position);
        return this;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentItem = position;
        recentSlideTime = System.currentTimeMillis();
        setCircleRes(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int getCurrentItem(){
        return mCurrentItem;
    }

    private void setCircleRes(int pos){
        for(int i=0;i<ll.getChildCount();i++){
            ImageView img = (ImageView) ll.getChildAt(i);
            img.setImageResource(R.drawable.circle_u);
        }
        ImageView img = (ImageView) ll.getChildAt(pos);
        img.setImageResource(R.drawable.circle_p);
    }

    public void showingState(boolean state){
        showingState = state;
    }

    public boolean getShowingState(){
        return showingState;
    }

    private static class WeakTimerTask extends TimerTask {
        private WeakReference<SlideView> mSlideViewWeakReference;

        public WeakTimerTask(SlideView mRollPagerView) {
            this.mSlideViewWeakReference = new WeakReference<>(mRollPagerView);
        }

        @Override
        public void run() {
            SlideView slideView = mSlideViewWeakReference.get();
            if (slideView!=null){
                if(slideView.showingState&&System.currentTimeMillis()-slideView.recentSlideTime>slideView.delay){
                    slideView.mHandler.sendEmptyMessage(0);
                }
            }else{
                cancel();
            }
        }
    }

    private final static class TimeTaskHandler extends Handler {
        private WeakReference<SlideView> mSlideViewWeakReference;

        public TimeTaskHandler(SlideView rollPagerView) {
            this.mSlideViewWeakReference = new WeakReference<>(rollPagerView);
        }

        @Override
        public void handleMessage(Message msg) {
            SlideView slideView = mSlideViewWeakReference.get();
            if(slideView==null){
                return;
            }
            int cur = slideView.vp.getCurrentItem()+1;
            if(cur>=slideView.vp.getAdapter().getCount()){
                cur=0;
            }
            slideView.vp.setCurrentItem(cur);
            if (slideView.vp.getAdapter().getCount()<=1) {
                slideView.stopPlay();
            }

        }
    }
    private TimeTaskHandler mHandler = new TimeTaskHandler(this);
}
