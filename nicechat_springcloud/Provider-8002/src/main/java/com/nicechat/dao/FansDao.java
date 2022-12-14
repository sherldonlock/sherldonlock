package com.nicechat.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FansDao {

    List<String> getFocusUserIdsByLoginUserId(String loginUserId);

    int addFocus(@Param("loginUserId") String loginUserId,
                 @Param("userId") String userId,
                 @Param("createTime") String createTime);

    int deleteFocus(@Param("loginUserId") String loginUserId,
                    @Param("userId") String userId);
}
