package com.sqq.sqq_total.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.gson.Gson;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.TcpService;
import com.sqq.sqq_total.data.Type;
import com.sqq.sqq_total.data.userBeen;
import com.sqq.sqq_total.databaseDao.User;
import com.sqq.sqq_total.databasehelper.DbUtil;
import com.sqq.sqq_total.rxbus.GetResponseEvent;
import com.sqq.sqq_total.rxbus.RxBus;
import com.sqq.sqq_total.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by sqq on 2016/8/12.
 */
public class LoginPresenter {

    public interface LoginView{
        public void LogindError();
        public void LoginSuccess();
        public void showProgress();
        public void hideProgress();
        public void netWorkError();
    }

    LoginView lv;
    String userId;
    String password;
    Gson gson;

    public LoginPresenter(LoginView loginView){
        lv = loginView;
        userId = "";
        password = "";
        gson = new Gson();
    }

    public Subscription registerResponseListener(final Context con){
        Subscription s = RxBus.getRxBus().toObservable(GetResponseEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetResponseEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        lv.netWorkError();
                        lv.hideProgress();
                    }

                    @Override
                    public void onNext(GetResponseEvent getResponseEvent) {
                        Log.d("Client", "onNext");
                        lv.hideProgress();
                        if(getResponseEvent.getType().equals(Type.NONE)){
                            lv.LogindError();
                        }
                        if(getResponseEvent.getType().equals(Type.LOGIN_BACK)){
                            PreferenceUtils.saveString(con, R.string.prefer_token, Type.LOGIN);

                            userBeen userbeen = gson.fromJson(getResponseEvent.getResponse(),userBeen.class);
                            PreferenceUtils.saveLong(con, R.string.prefer_userId, userbeen.getUserId());

                            User user = new User();
                            user.setPassword(userbeen.getPassword());
                            user.setUserId(userbeen.getUserId());
                            user.setAge(userbeen.getAge());
                            user.setNickName(userbeen.getNickName());
                            user.setPicUrl(userbeen.getPicUrl());
                            user.setSex(userbeen.getSex());
                            user.setTelNumber(userbeen.getTelNumber());
                            List<User> ret =  DbUtil.getUserService().query("where USER_ID=? limit 1",userbeen.getUserId()+"");
                            if(ret!=null && ret.size()==1){
                                Log.d("Client", "update");
                                user.setId(ret.get(0).getId());

                                DbUtil.getUserService().update(user);

                            }else{
                                Log.d("Client","save");
                                DbUtil.getUserService().save(user);
                            }
                            Log.d("Client","success");
                            lv.LoginSuccess();
                        }

                    }
                });
        return s;
    }

    public void attemptLogin(Context con,AutoCompleteTextView atv , EditText et) {
        atv.setError(null);
        et.setError(null);

        userId = atv.getText().toString();
        password = et.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(!isValidUserId(userId)){
            atv.setError(con.getString(R.string.log_userid_error));
            focusView = atv;
            cancel = true;
        }

        if(!isValidPassword(password)){
            et.setError(con.getString(R.string.log_userid_error));
            focusView = et;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            //登录.目前数据解析的方法太丑陋了

            userBeen user = new userBeen();
            user.setType(Type.LOGIN);
            user.setPassword(password);
            user.setUserId(Long.parseLong(userId));

            lv.showProgress();
            if(TcpService.getInstance().send(gson.toJson(user))){
                Log.d("Client", "发送成功"+gson.toJson(user));
            }else{
                lv.hideProgress();
                lv.netWorkError();
            }
        }
    }

    /**
     * 小于20位
     * @param userid
     * @return
     */

    private boolean isValidUserId(String userid){
        if(TextUtils.isEmpty(userid)||userid.length()>20){
            return false;
        }
        try {
            Long.parseLong(userid);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * 密码大于5位小于20位
     * @param password
     * @return
     */
    private boolean isValidPassword(String password){
        if(TextUtils.isEmpty(password)||password.length()<5||
                password.length()>20){
            return false;
        }
        return true;
    }
}
