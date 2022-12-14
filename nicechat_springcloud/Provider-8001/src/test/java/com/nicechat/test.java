package com.nicechat;

import cn.hutool.core.date.DateUtil;
import com.nicechat.dao.*;
import com.nicechat.pojo.*;
import com.nicechat.service.DynamicService;
import com.nicechat.service.SystemService;
import com.nicechat.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    SystemService systemService;

    @Autowired
    DynamicService dynamicService;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    DynamicDao dynamicDao;

    @Autowired
    ImageDao imageDao;

    @Autowired
    FansDao fansDao;

    @Autowired
    TopicDao topicDao;

    @Autowired
    ChatDao chatDao;

    @Test
    public void test1() {
        String avatarUrlByUserId = userDao.getAvatarUrlByUserId("51fe8ae9-8163-4898-86d9-b8b556454315");
        System.out.println(avatarUrlByUserId);
    }
}
