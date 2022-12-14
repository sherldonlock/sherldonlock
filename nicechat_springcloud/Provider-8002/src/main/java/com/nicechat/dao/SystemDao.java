package com.nicechat.dao;

import com.nicechat.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface SystemDao {

    int addUser(User user);

    int addUserUniversity(@Param("userId") String userId, @Param("universityCode") String universityCode);

    User getUserInfoByAccountNum(String accountNum);

    int getUserByUserNameOrAccountNum(@Param("userName") String userName,
                                       @Param("accountNum") String accountNum);
}
