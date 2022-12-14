package com.nicechat.service.impl;

import com.nicechat.dao.TopicDao;
import com.nicechat.pojo.Topic;
import com.nicechat.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicDao topicDao;

    @Override
    public List<Topic> getTopicList() {
        return topicDao.getTopicList();
    }

    @Override
    public List<Topic> getHotTopicsTopTen() {
        return topicDao.getHotTopicsTopTen();
    }
}
