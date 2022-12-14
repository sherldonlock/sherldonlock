package com.nicechat.service;

import com.nicechat.pojo.Message;
import com.nicechat.pojo.User;

import java.util.List;

public interface ChatSysService {

    int chatWith(String loginUserId, String userId);

    List<User> getUserList(String loginUserId);

    int saveMessage(Message chatMsg);
}
