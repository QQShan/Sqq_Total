package com.sqq.sqq_total.presenter;

import android.content.Context;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.utils.PreferenceUtils;

/**
 * Created by Administrator on 2016/7/13.
 */
public class InfoPresenter {

    public interface infoview{
        public void loadLoginedView();
        public void loadNoLoginView();
    }

    infoview iv;

    public InfoPresenter(infoview iView){
        iv = iView;
    }

    public void initView(Context con){
        String token = PreferenceUtils.getString(con, R.string.prefer_token, "");
        if(token!=null&&!token.equals("")){
            //身份认证通过的，也就是已经登录的
            iv.loadLoginedView();
        }else{
            iv.loadNoLoginView();
        }
    }
}
