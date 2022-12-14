package com.nicechat.service.impl;

import com.nicechat.dao.SystemDao;
import com.nicechat.dao.UserDao;
import com.nicechat.pojo.User;
import com.nicechat.service.SystemService;
import com.nicechat.utils.IDUtil;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    SystemDao systemDao;

    @Autowired
    UserDao userDao;

    @Override
    public int register(User registerUser) {
        int status1 = systemDao.addUser(registerUser);
        int status2 = systemDao.addUserUniversity(registerUser.getId(), registerUser.getSchoolCode());
        if (status1 > 0 && status2 > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public User getUserInfoByAccountNum(String accountNum) {
        return systemDao.getUserInfoByAccountNum(accountNum);
    }

    @Override
    public int checkUserName(String userName) {
        return systemDao.getUserByUserNameOrAccountNum(userName, "");
    }

    @Override
    public int checkAccountNum(String accountNum) {
        return systemDao.getUserByUserNameOrAccountNum("", accountNum);
    }
}
