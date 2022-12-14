package com.nicechat.dao;

import com.nicechat.pojo.Dynamic;
import com.nicechat.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    User getUserByAccountNum(String AccountNum);

    List<User> getUserNameList();

    List<User> getRecommendUsers(@Param("loginUserId") String loginUserId,
                                   @Param("schoolCode") String schoolCode);

    String getDynamicCountByUserId(String userId);

    String getFocusCountByUserId(String userId);

    String getFansCountByUserId(String userId);

    User getUserInfoByUserId(String userId);

    List<Dynamic> getDynamicListByUserId(String userId);

    List<User> getFocusListByUserId(String userId);

    List<User> getFansListByUserId(String userId);

    List<Dynamic> getMyLikeList(String userId);

    List<Dynamic> getMyCollectList(String userId);

    int updateAvatarUrl(@Param("userId") String userId,
                        @Param("avatarUrl") String avatarUrl);

    int updateUserInfo(User user);

    String getAvatarUrlByUserId(String userId);
}
