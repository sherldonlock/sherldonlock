package com.nicechat.service;

import com.nicechat.pojo.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> getTopicList();

    List<Topic> getHotTopicsTopTen();
}
