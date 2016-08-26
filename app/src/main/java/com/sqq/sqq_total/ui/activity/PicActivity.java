package com.sqq.sqq_total.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.adapter.BaseAdapter;
import com.sqq.sqq_total.presenter.PicActPresenter;
import com.sqq.sqq_total.servicedata.PicCommetItem;
import com.sqq.sqq_total.ui.fragment.BaseFragment;
import com.sqq.sqq_total.utils.TimerUtils;
import com.sqq.sqq_total.view.pulltorefresh.OnLoadListener;
import com.sqq.sqq_total.view.pulltorefresh.SqqRecyclerview;
import com.sqq.sqq_total.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class PicActivity extends BaseActivity implements PicActPresenter.PicActView,View.OnClickListener {

    Toolbar mToolbar;
    SqqRecyclerview rv;
    BaseAdapter adapter;

    PicActPresenter pap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);

        String url = getIntent().getExtras().getString(BaseFragment.bundleURL,"");
        String title = getIntent().getExtras().getString(BaseFragment.bundleTITLE, "");
        long id = getIntent().getExtras().getLong(BaseFragment.bundleID, -1);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView iv = (ImageView) findViewById(R.id.pic_top_img);
        Picasso.with(this).load(url).into(iv);

        pap = new PicActPresenter(this,id);
        startGetData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finishActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startGetData() {
        addSubscription(pap.loadItemData());
        rv.setRefreshing(true);
    }

    @Override
    public void initViews(final List<PicCommetItem> list) {
        Button bt = (Button) findViewById(R.id.publishBt);
        bt.setOnClickListener(this);
        ImageView im = (ImageView) findViewById(R.id.pic_top_img);
        im.setOnClickListener(this);

        rv = (SqqRecyclerview) findViewById(R.id.sqqrv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addSubscription(pap.loadItemData());
            }
        });
        rv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void OnLoadListener() {
                addSubscription(pap.loadMoreItemData());
            }
        });

        adapter = new BaseAdapter() {

            @Override
            public int getItemCount() {
                return list.size();
            }

            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                /*final TextView tv_title = holder.getView(R.id.ti_title);
                tv_title.setText(list.get(position).getTitle);*/
                final TextView tv_desc = holder.getView(R.id.ti_description);
                tv_desc.setText(list.get(position).getComment());
                final TextView tv_time = holder.getView(R.id.ti_time);
                tv_time.setText(TimerUtils.getTimeUpToNow(list.get(position).getTime(), PicActivity.this));

                /*ImageView im = holder.getView(R.id.ti_pic);
                Picasso.with(PicActivity.this).load(list.get(position).getPicUrl).into(im);*/
            }

            @Override
            protected int getLayoutID() {
                return R.layout.textitem;
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*intentTo(list.get(position).getTitle(), list.get(position).getUrl()
                        , HeadlineActivity.class);*/
            }
        });
        rv.setAdapter(adapter);
    }

    @Override
    public void getDataError(String info) {

    }

    @Override
    public void hideErrorView() {

    }

    @Override
    public void refresh(boolean isRefreshing) {
        adapter.notifyDataSetChanged();
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

    @Override
    public void publishSuccess() {
        addSubscription(pap.loadItemData());
    }

    @Override
    public void publishError() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publishBt:

                EditText et = (EditText) findViewById(R.id.comment);
                if(TextUtils.isEmpty(et.getText())){
                    return;
                }
                pap.publishComment(et.getText().toString(),this);

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                et.setText("");
                et.clearFocus();
                break;
            case R.id.pic_top_img:
                //跳转显示大图
                Log.d("picact","点击了图片");
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
        }
    }
}
