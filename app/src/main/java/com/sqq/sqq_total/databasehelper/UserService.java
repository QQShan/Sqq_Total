package com.sqq.sqq_total.databasehelper;

import com.sqq.sqq_total.databaseDao.User;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by Administrator on 2016/8/12.
 */
public class UserService extends BaseService<User,Integer> {

    public UserService(AbstractDao dao) {
        super(dao);
    }
}
