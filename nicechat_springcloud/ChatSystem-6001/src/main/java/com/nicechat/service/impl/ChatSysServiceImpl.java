package com.nicechat.service.impl;

import cn.hutool.core.date.DateUtil;
import com.nicechat.dao.ChatSysDao;
import com.nicechat.pojo.Message;
import com.nicechat.pojo.User;
import com.nicechat.service.ChatSysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatSysServiceImpl implements ChatSysService {

    /*private static ChatDao chatDao;

    @Autowired
    public void setChatDao(ChatDao chatDao) {
        ChatServiceImpl.chatDao = chatDao;
    }*/
    @Autowired
    ChatSysDao chatSysDao;

    @Override
    @Transactional
    public int chatWith(String loginUserId, String userId) {
        if (chatSysDao.countChatGroup(loginUserId, userId) > 0) {
            return 1;
        } else {
            return chatSysDao.addChatGroup(loginUserId, userId, DateUtil.now());
        }
    }

    @Override
    public List<User> getUserList(String loginUserId) {
        return chatSysDao.getUserListByLoginUserId(loginUserId);
    }

    @Override
    public int saveMessage(Message chatMsg) {
        return chatSysDao.addMessage(chatMsg);
    }
}
