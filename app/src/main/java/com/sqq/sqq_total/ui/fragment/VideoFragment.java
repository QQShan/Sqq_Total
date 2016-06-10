package com.sqq.sqq_total.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.adapter.BaseAdapter;
import com.sqq.sqq_total.view.SlideView;
import com.sqq.sqq_total.viewholder.BaseViewHolder;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/5/30.
 */
public class VideoFragment extends BaseFragment {

    RecyclerView rv;
    BaseAdapter adapter;

    private int resId[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3
            , R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8
            , R.drawable.img9, R.drawable.img10, R.drawable.img11
            , R.drawable.img12, R.drawable.img13, R.drawable.img14};
    private String des[] = {"云层里的阳光", "好美的海滩", "好美的海滩", "夕阳西下的美景", "夕阳西下的美景"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景", "好美的海滩","好美的海滩", "好美的海滩"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景"};

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_video,container,false);
        rv = (RecyclerView) rootView.findViewById(R.id.video_rv);
        rv.setLayoutManager(new GridLayoutManager(getSelfActivity(), 2));
        rv.setItemAnimator(new DefaultItemAnimator());

        adapter = new BaseAdapter() {

            @Override
            public int getItemCount() {
                return resId.length;
            }

            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                final TextView tv = holder.getView(R.id.name);
                tv.setText(des[position]);
                ImageView im = holder.getView(R.id.pic);
                im.setImageResource(resId[position]);
                TextView tv1 = holder.getView(R.id.info_time);
                tv1.setText("1小时以前");
                TextView tv2 = holder.getView(R.id.info_counts);
                tv2.setText("好几万人看");
            }

            @Override
            protected int getLayoutID() {
                return R.layout.videoitem;
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("sqqq", "点击了"+position);
            }
        });

        rv.setAdapter(adapter);

    }

}
