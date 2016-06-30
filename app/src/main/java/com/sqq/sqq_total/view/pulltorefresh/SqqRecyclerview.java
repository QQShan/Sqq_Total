package com.sqq.sqq_total.view.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.sqq.sqq_total.R;

/**
 * 这里还可以优化的地方，最好不要在上拉加载更多的时候还能下拉刷新，不然数据就可能会乱
 * 需要线程安全的处理
 * 目前实现了 下拉刷新的时候不能执行加载更多
 * Created by Administrator on 2016/6/30.
 * recyclerView.scrollToPosition()方法把想要显示的显示，如果已经显示了，就不做任何事情
 * 如果没有显示就置顶显示
 */
public class SqqRecyclerview extends LinearLayout {
    /**
     * 有没有在上拉加载中
     */
    private boolean isLoading = false;
    OnLoadListener onLoadListener;

    private static final int[] DEFAULT_COLOR_RES = new int[]{android.R.color.holo_blue_light, android.R.color.holo_red_light,
            android.R.color.holo_orange_light, android.R.color.holo_green_light};

    SwipeRefreshLayout swipeRefreshLayout;
    View footerView;
    RecyclerView recyclerView;
    Context context;

    /**
     * 滑动状态
     * 0表示没有在滑动
     */
    //private int STATE=-1;

    public SqqRecyclerview(Context context) {
        this(context, null);
    }

    public SqqRecyclerview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SqqRecyclerview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SqqRecyclerview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        this.context = context;

        swipeRefreshLayout = new SwipeRefreshLayout(context,attrs);
        swipeRefreshLayout.setColorSchemeResources(DEFAULT_COLOR_RES);

        recyclerView = new RecyclerView(context,attrs);
        recyclerView.setId(android.R.id.list);

        swipeRefreshLayout.addView(recyclerView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        addView(swipeRefreshLayout,
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));

        /**
         * 更好的做法不是在这里做最后一条之类的判断
         * 后续会加到touch事件里做判断
         */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //STATE = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("sqqq","dy:"+dy);
                if (!swipeRefreshLayout.isRefreshing()
                        && isLastItemVisible(recyclerView) && !isLoading
                        && !isFirstItemVisible(recyclerView) &&/*STATE!=0*/dy!=0) {
                    //最后一条可见，并且没有在加载中
                    startLoad();
                }
            }
        });
    }

    /*private boolean isFooterViewShowing(){
        return footerView.isShown();
    }*/

    private void startLoad(){
        //开始加载之后recyclerview上移，footerview显示
        if(onLoadListener!=null){
            footerView.setVisibility(View.VISIBLE);

            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
            isLoading = true;
            onLoadListener.OnLoadListener();
        }
    }

    /**
     * 如果有加载到数据
     * 结束加载更多
     */
    public void endLoadRefresh(){
        footerView.setVisibility(View.GONE);
        /**
         * 这里非正规做法，这里只要向上移动一段距离就行，不管多少
         * 最好露出一点，让用户知道你加载了数据
         * 全部露出的话，在只加载了一条数据的时候会有问题
         */
        recyclerView.scrollBy(0,-10);
        isLoading = false;
    }

    public void endLoadRefreshWithNoData(){
        footerView.setVisibility(View.GONE);
        isLoading = false;
    }

    public void setRefreshing(boolean ing){
        swipeRefreshLayout.setRefreshing(ing);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener){
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void setOnLoadListener(OnLoadListener listener){
        onLoadListener  = listener;
        if(onLoadListener!=null){
            addfooterView();
        }
    }

    private void addfooterView(){
        footerView = LayoutInflater.from(context).inflate(R.layout.refreshfooterview,this,false);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //lp.gravity = Gravity.BOTTOM;
        footerView.setLayoutParams(lp);
        addView(footerView);
        footerView.setVisibility(View.GONE);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        if (adapter == null)
            throw new NullPointerException("mAdapter is null please call CygSwipeRefreshLayout.setAdapter");
        recyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        recyclerView.setLayoutManager(layout);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator){
        recyclerView.setItemAnimator(animator);
    }

    /**
     * 判断第一个条目是否完全可见
     *
     * @param recyclerView
     * @return
     */
    private boolean isFirstItemVisible(RecyclerView recyclerView) {
        final RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            return true;
        }
        // 第一个条目完全展示,可以刷新
        if (getFirstVisiblePosition(recyclerView) == 0) {
            return recyclerView.getChildAt(0).getTop() >= recyclerView.getTop();
        }
        return false;
    }

    /**
     * 获取第一个可见子View的位置下标
     *
     * @param recyclerView
     * @return
     */
    private int getFirstVisiblePosition(RecyclerView recyclerView) {
        View firstVisibleChild = recyclerView.getChildAt(0);
        return firstVisibleChild != null ?
                recyclerView.getChildAdapterPosition(firstVisibleChild) : -1;
    }

    /**
     * 判断最后一个条目是否完全可见
     *
     * @param recyclerView
     * @return
     */
    private boolean isLastItemVisible(RecyclerView recyclerView) {
        final RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
        // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            return false;
        }
        // 最后一个条目View完全展示,可以刷新
        int lastVisiblePosition = getLastVisiblePosition(recyclerView);
        if (lastVisiblePosition >= recyclerView.getAdapter().getItemCount() - 1) {
            return recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom()
                    <= recyclerView.getBottom();
        }
        return false;
    }

    /**
     * 获取最后一个可见子View的位置下标
     *
     * @param recyclerView
     * @return
     */
    private int getLastVisiblePosition(RecyclerView recyclerView) {
        View lastVisibleChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        return lastVisibleChild != null ? recyclerView.getChildAdapterPosition(lastVisibleChild) : -1;
    }
}
