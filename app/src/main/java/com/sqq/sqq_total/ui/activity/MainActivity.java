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
import com.sqq.sqq_total.ui.fragment.BaseFragment;
import com.sqq.sqq_total.ui.fragment.LeftFragment;
import com.sqq.sqq_total.ui.fragment.RightFragment;

/**
 * Created by sqq on 2016/5/28.
 */
public class MainActivity extends AppCompatActivity implements MainPresenter.MainView{

    public MainPresenter mainPresenter;
    LeftFragment leftFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);

        leftFragment = new LeftFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_context, leftFragment
                        , leftFragment.getClass().getName())
                .commit();
    }


    @Override
    public void setText(String text) {

    }

    @Override
    public void onBackPressed() {
        if(!leftFragment.onBackPressed())
            super.onBackPressed();
    }
}