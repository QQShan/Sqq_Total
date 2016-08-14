package com.sqq.sqq_total.presenter;

import android.content.Context;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.databaseDao.User;
import com.sqq.sqq_total.databasehelper.DbUtil;
import com.sqq.sqq_total.utils.PreferenceUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class InfoPresenter {

    public interface infoview{
        public void loadLoginedView(User user);
        public void loadNoLoginView();
    }

    infoview iv;
    User user;

    public InfoPresenter(infoview iView){
        iv = iView;
    }

    public void initView(Context con){
        String token = PreferenceUtils.getString(con, R.string.prefer_token, "");
        if(token!=null&&!token.equals("")){
            //身份认证通过的，也就是已经登录的
            user = null;
            List<User> list = DbUtil.getUserService().query("where USER_ID=? limit 1",
                    PreferenceUtils.getLong(con, R.string.prefer_userId, -1L) + "");
            if(list!=null&&list.size()==1){
                user = list.get(0);
            }
            iv.loadLoginedView(user);
        }else{
            iv.loadNoLoginView();
        }
    }
}
