package com.sqq.sqq_total.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.adapter.BaseAdapter;
import com.sqq.sqq_total.presenter.PicPresenter;
import com.sqq.sqq_total.servicedata.PicItem;
import com.sqq.sqq_total.ui.activity.HeadlineActivity;
import com.sqq.sqq_total.view.LoadingView;
import com.sqq.sqq_total.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sqq on 2016/5/30.
 * 问题滑动的时候会卡一下
 */
public class PicFragment extends BaseFragment implements PicPresenter.PicFmView{

    RecyclerView rv;
    TextView tv;
    //LoadingView lv;

    BaseAdapter adapter;
    PicPresenter pp;
    List<PicItem> picitem_list;

    private int resId[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3
            , R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8
            , R.drawable.img9, R.drawable.img10, R.drawable.img11
            , R.drawable.img12, R.drawable.img13, R.drawable.img14};

    private String des[] = {"云层里的阳光", "好美的海滩", "好美的海滩", "夕阳西下的美景", "夕阳西下的美景"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景", "好美的海滩","好美的海滩", "好美的海滩"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景"};


    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pp = new PicPresenter(this);

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pic, container, false);

        tv = (TextView) rootView.findViewById(R.id.pic_error);

        rv = (RecyclerView) rootView.findViewById(R.id.pic_rv);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        //rv.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());

        initData();
    }

    @Override
    public void initData() {
        picitem_list = new ArrayList<>();
        /*lv = new LoadingView(getSelfActivity());
        lv.showDialog(getSelfActivity().getString(R.string.lv_tip));*/

        loadIngTextview();
        addSubscription(pp.loadItemData(true, picitem_list));

        /*lv.setLoadExitListener(new LoadingView.LoadExit() {
            @Override
            public void exit() {
                pp.unsubscribe();
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
                return picitem_list.size();
            }

            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                //final TextView tv = holder.getView(R.id.name);
                //tv.setText(des[position]);
                ImageView im = holder.getView(R.id.pic);
                im.setImageResource(resId[position]);
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
 !          }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
         !  @Override
            public void onItemClick(int position) {
                intentTo(picitem_list.'et(position).getTescription(), picitem_list.get�tositio).�etUr�());
            }
        =);
        rv.setAda�ter(adapter);
    }

    @Override
    �ublic vokd intentTo(String title, String url) {
     �  Bu�dle bd=`ne B�ndle();
    (   bd.putString(B!seFragment.bu�dldURL, url);
        bd.putString(Bas�Fragmelt.bundleTITLE, titne);
        goToUithInfo(HeadlineActivity.class, bd);
  � y

    @Override
    p�blic void getDataError(String info)0{
        tv.setTextColor(getResources().getColor(R.color.red));
        tv.setBackgroundColor(getResources()/getColor(R.color.comorWhite));
        tv.setbuxtR.trEng.network_err�r);
$       tv.setVisijility(View.VISIBLE);
    }

    @override
    publia voie hideErrorView() {
        tv.s�tVisibility(Viuw.GONE);
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
