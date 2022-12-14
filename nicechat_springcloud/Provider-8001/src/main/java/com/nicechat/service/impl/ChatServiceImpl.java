package com.nicechat.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.sql.visitor.functions.If;
import com.nicechat.dao.ChatDao;
import com.nicechat.dao.UserDao;
import com.nicechat.pojo.Dialog;
import com.nicechat.pojo.Message;
import com.nicechat.pojo.User;
import com.nicechat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatDao chatDao;

    @Autowired
    UserDao userDao;

    @Override
    @Transactional
    public int chatWith(String loginUserId, String userId) {
        if (chatDao.countChatGroup(loginUserId, userId) > 0) {
            return 1;
        } else {
            return chatDao.addChatGroup(loginUserId, userId, DateUtil.now());
        }
    }

    @Override
    public List<User> getUserList(String loginUserId) {
        return chatDao.getUserListByLoginUserId(loginUserId);
    }

    @Override
    public List<Dialog> getDialog(String loginUserId, String userId) {
        ArrayList<Dialog> dialogs = new ArrayList<>();
        List<Message> messages = chatDao.getDialog(loginUserId, userId);
        for (Message message : messages) {
            Dialog dialog = new Dialog();
            dialog.setUserId(message.getSenderId());
            dialog.setAvatarUrl(userDao.getAvatarUrlByUserId(message.getSenderId()));
            dialog.setContent(message.getContent());
            dialog.setSendTime(message.getSendTime().substring(0, 19));
            dialogs.add(dialog);
        }
        return dialogs;
    }

    @Override
    public int updateReadFlag(String loginUserId, String userId) {
        return chatDao.updateReadFlag(loginUserId, userId);
    }
}
