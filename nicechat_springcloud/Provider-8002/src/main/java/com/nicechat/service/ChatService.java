package com.nicechat.service;

import com.nicechat.pojo.Dialog;
import com.nicechat.pojo.User;

import java.util.List;

public interface ChatService {

    int chatWith(String loginUserId, String userId);

    List<User> getUserList(String loginUserId);

    List<Dialog> getDialog(String loginUserId, String userId);

    int updateReadFlag(String loginUserId, String userId);
}
