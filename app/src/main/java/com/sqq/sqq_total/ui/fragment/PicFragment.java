package com.sqq.sqq_total.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.adapter.BaseAdapter;
import com.sqq.sqq_total.presenter.PicPresenter;
import com.sqq.sqq_total.servicedata.PicItem;
import com.sqq.sqq_total.ui.activity.HeadlineActivity;
import com.sqq.sqq_total.ui.activity.PicActivity;
import com.sqq.sqq_total.view.LoadingView;
import com.sqq.sqq_total.view.pulltorefresh.OnLoadListener;
import com.sqq.sqq_total.view.pulltorefresh.SqqRecyclerview;
import com.sqq.sqq_total.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by sqq on 2016/5/30.
 * 问题滑动的时候会卡一下
 */
public class PicFragment extends BaseFragment implements PicPresenter.PicFmView{

    SqqRecyclerview rv;
    TextView tv;
    //LoadingView lv;

    BaseAdapter adapter;
    PicPresenter pp;

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pic, container, false);
        pp = new PicPresenter(this);
        startGetData();
    }

    @Override
    public void initViews(final List<PicItem> picitem_list) {
        tv = (TextView) rootView.findViewById(R.id.pic_error);

        rv = (SqqRecyclerview) rootView.findViewById(R.id.sqqrv);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        //rv.setLayoutManager(new LinearLayoutManager(getSelfActivity(), LinearLayout.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addSubscription(pp.loadItemData());
            }
        });
        rv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void OnLoadListener() {
                addSubscription(pp.loadMoreItemData());
            }
        });

        adapter = new BaseAdapter() {
            @Override
            public int getItemCount() {
                return picitem_list.size();
            }

            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                //final TextView tv = holder.getView(R.id.name);
                //tv.setText(des[position]);
                Log.d("sqqq", "position" + position);
                ImageView im = holder.getView(R.id.pic);
                Picasso.with(getSelfActivity()).load(picitem_list.get(position).getSmallPicUrl())
                        .into(im);

                //im.setImageResource(R.mipmap.ic_launcher);
                //下面的功能是计算出图片的显著的颜色赋值给textview，这个功能可以去掉
                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId[position]);
                //异步获得bitmap图片颜色值
                /*Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant = palette.getVibrantSwatch();//有活力
                        Palette.Swatch c = palette.getDarkVibrantSwatch();  //有活力 暗色
                        Palette.Swatch d = palette.getLightVibrantSwatch(); //有活力 亮色
                        Palette.Swatch f = palette.getMutedSwatch();        //柔和
                        Palette.Swatch a = palette.getDarkMutedSwatch();    //柔和 暗色
                        Palette.Swatch b = palette.getLightMutedSwatch();   //柔和 亮色

                        if (vibrant != null) {
                            int color1 = vibrant.getBodyTextColor();        //内容颜色
                            int color2 = vibrant.getTitleTextColor();       //标题颜色
                            int color3 = vibrant.getRgb();                  //rgb颜色

                            tv.setBackgroundColor(
                                    vibrant.getRgb());
                            tv.setTextColor(
                                    vibrant.getTitleTextColor());
                        }
                    }
                });*/
            }

            @Override
            protected int getLayoutID() {
                return R.layout.picitem;
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intentTo(picitem_list.get(position).getTitle(), picitem_list.get(position).getPicUrl()
                        , picitem_list.get(position).getId(), PicActivity.class);
            }
        });
        rv.setAdapter(adapter);
    }

    @Override
    public void startGetData() {

        /*lv = new LoadingView(getSelfActivity());
        lv.showDialog(getSelfActivity().getString(R.string.lv_tip));*/

        loadIngTextview();
        addSubscription(pp.loadItemData());

        /*lv.setLoadExitListener(new LoadingView.LoadExit() {
            @Override
            public void exit() {
                pp.unsubscribe();
            }
        });*/
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
        adapter.notifyDataSetChanged();

        //lv.dismissDialog();
        loadTextviewEnd();
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
