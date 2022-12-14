package com.nicechat.dao;

import com.nicechat.pojo.Message;
import com.nicechat.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatSysDao {

    int addChatGroup(@Param("loginUserId") String loginUserId,
                     @Param("userId") String userId,
                     @Param("createTime") String createTime);

    int countChatGroup(@Param("loginUserId") String loginUserId,
                       @Param("userId") String userId);

    List<User> getUserListByLoginUserId(String loginUserId);

    int addMessage(Message message);
}
