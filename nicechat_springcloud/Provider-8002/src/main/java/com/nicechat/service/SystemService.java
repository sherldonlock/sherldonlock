package com.nicechat.service;

import com.nicechat.pojo.User;

public interface SystemService {

    int register(User user);

    User getUserInfoByAccountNum(String accountNum);

    int checkUserName(String userName);

    int checkAccountNum(String accountNum);
}
