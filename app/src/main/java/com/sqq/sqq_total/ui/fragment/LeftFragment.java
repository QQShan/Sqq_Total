package com.sqq.sqq_total.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.ui.activity.CaptureActivity;
import com.sqq.sqq_total.ui.activity.InfoActivity;
import com.sqq.sqq_total.ui.activity.MainActivity;

/**
 * Created by Administrator on 2016/5/30.
 */
public class LeftFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener{

    private PagerAdapter mPagerAdapter;

    ViewPager vp;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_containleft, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getSelfActivity()).setSupportActionBar(toolbar);

        vp = (ViewPager) rootView.findViewById(R.id.left_viewpager);
        //前后缓存三页
        vp.setOffscreenPageLimit(3);
        mPagerAdapter = new MyViewPageAdapter(getChildFragmentManager());
        vp.setAdapter(mPagerAdapter);

        TabLayout mTab = (TabLayout) rootView.findViewById(R.id.tab);
        mTab.setupWithViewPager(vp);

        mTab.getTabAt(0).setText(R.string.t_exlpore);
        mTab.getTabAt(1).setText(R.string.t_text);
        mTab.getTabAt(2).setText(R.string.t_pic);
        mTab.getTabAt(3).setText(R.string.t_video);

        drawer = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getSelfActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) rootView.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        /*super.onCreateOptionsMenu(menu, inflater);*/
        Log.d("sqqqq","onCreateOptionsMenu");
        inflater.inflate(R.menu.menu_leftfragment, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Qr_scan:
                goTo(CaptureActivity.class);
                break;
            case R.id.upload_pic:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_main:
                goTo(MainActivity.class);
                break;
            case R.id.nav_info:
                goTo(InfoActivity.class);
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_changeSkin:
                break;
        }
        drawer = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
