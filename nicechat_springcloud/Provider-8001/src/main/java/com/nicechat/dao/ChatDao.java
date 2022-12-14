package com.nicechat.dao;

import com.nicechat.pojo.Message;
import com.nicechat.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatDao {

    int addChatGroup(@Param("loginUserId") String loginUserId,
                     @Param("userId") String userId,
                     @Param("createTime") String createTime);

    int countChatGroup(@Param("loginUserId") String loginUserId,
                      @Param("userId") String userId);

    List<User> getUserListByLoginUserId(String loginUserId);

    List<Message> getDialog(@Param("loginUserId") String loginUserId,
                            @Param("userId") String userId);

    int updateReadFlag(@Param("loginUserId") String loginUserId,
                        @Param("userId") String userId);
}
