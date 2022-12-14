package com.nicechat.service.impl;

import cn.hutool.core.date.DateUtil;
import com.nicechat.dao.DynamicDao;
import com.nicechat.dao.FansDao;
import com.nicechat.dao.SystemDao;
import com.nicechat.dao.UserDao;
import com.nicechat.pojo.Dynamic;
import com.nicechat.pojo.User;
import com.nicechat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    DynamicDao dynamicDao;

    @Autowired
    FansDao fansDao;

    @Autowired
    SystemDao systemDao;

    @Override
    public List<User> getUserNameList() {
        return userDao.getUserNameList();
    }

    @Override
    public List<User> getRecommendList(String loginUserId, String schoolCode) {
        List<User> recommendUsers = userDao.getRecommendUsers(loginUserId, schoolCode);
        for (User recommendUser : recommendUsers) {
            String userId = recommendUser.getId();
            recommendUser.setDynamicCount(userDao.getDynamicCountByUserId(userId));
            recommendUser.setFocusCount(userDao.getFocusCountByUserId(userId));
            recommendUser.setFansCount(userDao.getFansCountByUserId(userId));
        }
        return recommendUsers;
    }

    @Override
    public User getUserInfo(String userId) {
        User user = userDao.getUserInfoByUserId(userId);
        user.setDynamicCount(userDao.getDynamicCountByUserId(userId));
        user.setFocusCount(userDao.getFocusCountByUserId(userId));
        user.setFansCount(userDao.getFansCountByUserId(userId));
        return user;
    }

    @Override
    public List<Dynamic> getDynamicListByUserId(String userId) {
        return userDao.getDynamicListByUserId(userId);
    }

    @Override
    public List<User> getFocusList(String userId) {
        return userDao.getFocusListByUserId(userId);
    }

    @Override
    public List<User> getFansList(String userId) {
        return userDao.getFansListByUserId(userId);
    }

    @Override
    public List<Dynamic> getMyLikeList(String userId) {
        return userDao.getMyLikeList(userId);
    }

    @Override
    public List<Dynamic> getMyCollectList(String userId) {
        return userDao.getMyCollectList(userId);
    }

    @Override
    public List<String> getMyFocusIdList(String userId) {
        return fansDao.getFocusUserIdsByLoginUserId(userId);
    }

    @Override
    @Transactional
    public int cancelFocus(String loginUserId, String userId) {
        return fansDao.deleteFocus(loginUserId, userId);
    }

    @Override
    @Transactional
    public int focus(String loginUserId, String userId) {
        return fansDao.addFocus(loginUserId, userId, DateUtil.now());
    }

    @Override
    @Transactional
    public int uploadAvatar(String userId, String avatarUrl) {
        return userDao.updateAvatarUrl(userId, avatarUrl);
    }

    @Override
    @Transactional
    public User updateUserInfo(User user) {
        userDao.updateUserInfo(user);
        return systemDao.getUserInfoByAccountNum(user.getAccountNum());
    }
}
