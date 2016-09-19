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

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by sqq on 2016/8/13.
 */
public class RegisterPresenter {

    public interface RegisterView{
        public void regError();
        public void regSuccess();
        public void showProgress();
        public void hideProgress();
        public void netWorkError();
    }

    RegisterView lv;
    String userId;
    String password;
    Gson gson;

    public RegisterPresenter(RegisterView lv){
        this.lv = lv;
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
                            //已经存在
                            lv.regError();
                        }
                        if(getResponseEvent.getType().equals(Type.REGISTER_BACK)){
                            //因为这里只返回了一个type，所以这里就只能群发用户名和密码，其他属性给默认值

                            PreferenceUtils.saveString(con, R.string.prefer_token, Type.LOGIN);

                            PreferenceUtils.saveLong(con, R.string.prefer_userId, Long.parseLong(userId));

                            User user = new User();
                            user.setPassword(password);
                            user.setUserId(Long.parseLong(userId));
                            user.setAge(0);
                            user.setNickName("");
                            user.setPicUrl("");//这里应该给一个默认的图片地址
                            user.setSex(1);
                            user.setTelNumber("");
                            List<User> ret =  DbUtil.getUserService().query("where USER_ID=? limit 1",userId);
                            if(ret!=null && ret.size()==1){
                                Log.d("Client", "update");
                                user.setId(ret.get(0).getId());

                                DbUtil.getUserService().update(user);

                            }else{
                                Log.d("Client","save");
                                DbUtil.getUserService().save(user);
                            }
                            Log.d("Client", "success");
                            lv.regSuccess();
                        }

                    }
                });
        return s;
    }


    public void attemptReg(Context con,AutoCompleteTextView atv , EditText et){
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
            et.setError(con.getString(R.string.log_password_error));
            focusView = et;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            //注册
            userBeen user = new userBeen();
            user.setType(Type.REGISTER);
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
            Integer.parseInt(userid);
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
