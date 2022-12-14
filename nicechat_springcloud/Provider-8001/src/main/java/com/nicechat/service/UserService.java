package com.nicechat.service;

import com.nicechat.pojo.Dynamic;
import com.nicechat.pojo.User;

import java.util.List;

public interface UserService {

    List<User> getUserNameList();

    List<User> getRecommendList(String loginUserId, String schoolCode);

    User getUserInfo(String userId);

    List<Dynamic> getDynamicListByUserId(String userId);

    List<User> getFocusList(String userId);

    List<User> getFansList(String userId);

    List<Dynamic> getMyLikeList(String userId);

    List<Dynamic> getMyCollectList(String userId);

    List<String> getMyFocusIdList(String userId);

    int cancelFocus(String loginUserId, String userId);

    int focus(String loginUserId, String userId);

    int uploadAvatar(String userId, String avatarUrl);

    User updateUserInfo(User user);
}
