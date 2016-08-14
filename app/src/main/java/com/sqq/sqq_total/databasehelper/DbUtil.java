package com.sqq.sqq_total.databasehelper;

import com.sqq.sqq_total.databaseDao.UserDao;

/**
 * Created by sqq on 2016/8/12.
 */
public class DbUtil {

    private static UserService userService;

    private static UserDao getUserDao(){
        return DbCore.getDaoSession().getUserDao();
    }

    public static UserService getUserService(){
        if(userService == null){
            userService = new UserService(getUserDao());
        }
        return userService;
    }
}
