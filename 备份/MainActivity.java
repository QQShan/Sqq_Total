package com.sqq.sqq_total.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.presenter.MainPresenter;
import com.sqq.sqq_total.ui.fragment.LeftFragment;
import com.sqq.sqq_total.ui.fragment.RightFragment;

/**
 * Created by sqq on 2016/5/28.
 */
public class MainActivity extends AppCompatActivity implements MainPresenter.MainView,View.OnClickListener{

    public MainPresenter mainPresenter;
    static final String KEY_VALUE="KEY_VALUE";
    LeftFragment leftFragment;
    RightFragment rightFragment;

    static final int INDEX_LEFT=0;
    static final int INDEX_RIGHT=1;

    public Fragment mCurFragmet;
    TextView tv_left,tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);
        tv_left = (TextView) findViewById(R.id.left);
        tv_right = (TextView) findViewById(R.id.right);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

        if(savedInstanceState!=null){
            // 在内存重启的时候调用
            leftFragment = (LeftFragment) getSupportFragmentManager()
                    .findFragmentByTag(leftFragment.getClass().getName());
            rightFragment = (RightFragment) getSupportFragmentManager()
                    .findFragmentByTag(rightFragment.getClass().getName());
            Fragment show,hide;
            int index = savedInstanceState.getInt(KEY_VALUE);
            if(index==INDEX_LEFT){
                show = leftFragment;
                hide = rightFragment;
            }else{
                show = rightFragment;
                hide = leftFragment;
            }
            getSupportFragmentManager().beginTransaction()
                    .show(show)
                    .hide(hide)
                    .commit();
            mCurFragmet = show;
        }else {
            leftFragment = new LeftFragment();
            rightFragment = new RightFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.id_context, leftFragment
                            , leftFragment.getClass().getName())
                    .add(R.id.id_context, rightFragment
                            , rightFragment.getClass().getName())
                    .hide(rightFragment)
                    //.hide(leftFragment)
                    .commit();
            mCurFragmet = leftFragment;
            //mCurFragmet = rightFragment;

        }
        whichViewToShow();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mCurFragmet instanceof LeftFragment)
            outState.putInt(KEY_VALUE,INDEX_LEFT);
        else
            outState.putInt(KEY_VALUE,INDEX_RIGHT);
    }

    @Override
    public void setText(String text) {

    }

    @Override
    public void onClick(View v) {
        Fragment oldFragment = mCurFragmet;
        switch (v.getId()){
            case R.id.left:
                if(mCurFragmet instanceof LeftFragment)
                    return;

                mCurFragmet = leftFragment;
                break;
            case R.id.right:
                if(mCurFragmet instanceof RightFragment)
                    return;
                mCurFragmet = rightFragment;
                break;
            default:
                break;
        }
        whichViewToShow();
        getSupportFragmentManager()
                .beginTransaction()
                .hide(oldFragment)
                .show(mCurFragmet)
                //.replace(R.id.id_context, mCurFragmet)
                //.addToBackStack(null)
                .commit();
    }




    public void whichViewToShow(){
        tv_left.setTextColor(getResources().getColor(R.color.colorWhite));
        Drawable l_top = getResources().getDrawable(R.drawable.main_index_my_normal);
        l_top.setBounds(0, 0, l_top.getMinimumWidth(), l_top.getMinimumHeight());
        tv_left.setCompoundDrawables(null, l_top, null, null);

        tv_right.setTextColor(getResources().getColor(R.color.colorWhite));
        Drawable r_top = getResources().getDrawable(R.drawable.main_index_more_normal);
        r_top.setBounds(0, 0, r_top.getMinimumWidth(), r_top.getMinimumHeight());
        tv_right.setCompoundDrawables(null, r_top, null, null);

        if(mCurFragmet instanceof LeftFragment){
            tv_left.setTextColor(getResources().getColor(R.color.colorYellow));
            l_top = getResources().getDrawable(R.drawable.main_index_my_pressed);
            l_top.setBounds(0, 0, l_top.getMinimumWidth(), l_top.getMinimumHeight());
            tv_left.setCompoundDrawables(null,l_top,null,null);
        }else{
            tv_right.setTextColor(getResources().getColor(R.color.colorYellow));
            r_top = getResources().getDrawable(R.drawable.main_index_more_pressed);
            r_top.setBounds(0, 0, r_top.getMinimumWidth(), r_top.getMinimumHeight());
            tv_right.setCompoundDrawables(null, r_top, null, null);
        }

    }

}
