package com.sqq.sqq_total.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

        mCurFragmet = new LeftFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_context, mCurFragmet).commit();
        tv_left.setTextColor(getResources().getColor(R.color.colorYellow));
        Drawable top = getResources().getDrawable(R.drawable.main_index_my_pressed);
        top.setBounds(0,0,top.getMinimumWidth(),top.getMinimumHeight());
        tv_left.setCompoundDrawables(null, top, null, null);
    }

    @Override
    public void setText(String text) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left:
                if(mCurFragmet instanceof LeftFragment)
                    return;
                changeFm(R.id.left);
                break;
            case R.id.right:
                if(mCurFragmet instanceof RightFragment)
                    return;
                changeFm(R.id.right);
                break;
            default:
                break;
        }
    }

    private void changeFm(int id){
        tv_left.setTextColor(getResources().getColor(R.color.colorWhite));
        Drawable l_top = getResources().getDrawable(R.drawable.main_index_my_normal);
        l_top.setBounds(0,0,l_top.getMinimumWidth(),l_top.getMinimumHeight());
        tv_left.setCompoundDrawables(null,l_top,null,null);

        tv_right.setTextColor(getResources().getColor(R.color.colorWhite));
        Drawable r_top = getResources().getDrawable(R.drawable.main_index_more_normal);
        r_top.setBounds(0,0,r_top.getMinimumWidth(),r_top.getMinimumHeight());
        tv_right.setCompoundDrawables(null,r_top,null,null);

        switch(id){
            case R.id.left:
                mCurFragmet = new LeftFragment();
                tv_left.setTextColor(getResources().getColor(R.color.colorYellow));
                l_top = getResources().getDrawable(R.drawable.main_index_my_pressed);
                l_top.setBounds(0, 0, l_top.getMinimumWidth(), l_top.getMinimumHeight());
                tv_left.setCompoundDrawables(null,l_top,null,null);
                break;
            case R.id.right:
                mCurFragmet = new RightFragment();
                tv_right.setTextColor(getResources().getColor(R.color.colorYellow));
                r_top = getResources().getDrawable(R.drawable.main_index_more_pressed);
                r_top.setBounds(0, 0, r_top.getMinimumWidth(), r_top.getMinimumHeight());
                tv_right.setCompoundDrawables(null,r_top,null,null);
                break;
            default:
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.id_context, mCurFragmet)
                /*.addToBackStack(null)*/
                .commit();
    }

}
