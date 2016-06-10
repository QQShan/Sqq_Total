package com.sqq.sqq_total.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sqq.sqq_total.R;

/**
 * Created by sqq on 2016/6/2.
 * 一开始作为头条页的轮播图的内容，但是后来发现作为轮播图的内容的话用fragment有些浪费，
 * 而且有些问题，点击头条页的item跳转到其他activity后，隔个一段时间回去轮播图会有一段时间空白
 */
public class viewpagerFragment extends BaseFragment {

    private int resId[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3
            , R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8
            , R.drawable.img9, R.drawable.img10, R.drawable.img11
            , R.drawable.img12, R.drawable.img13, R.drawable.img14};
    private String des[] = {"云层里的阳光", "好美的海滩", "好美的海滩", "夕阳西下的美景", "夕阳西下的美景"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景", "好美的海滩","好美的海滩", "好美的海滩"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景"};

    int position;
    public static viewpagerFragment newInstance(int position) {
        viewpagerFragment fragment = new viewpagerFragment();
        fragment.position = position;
        return fragment;
    }


    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            position = savedInstanceState.getInt("position");
        }
        rootView = (ViewGroup) inflater.inflate(R.layout.views,container,false);
        rootView.setClickable(true);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sqqq", "点击了" + position);

            }
        });

        ImageView img = (ImageView) rootView.findViewById(R.id.img);
        img.setImageResource(resId[position]);
        TextView tv = (TextView) rootView.findViewById(R.id.tv);
        tv.setText(des[position]);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", position);
        super.onSaveInstanceState(outState);
    }

}
