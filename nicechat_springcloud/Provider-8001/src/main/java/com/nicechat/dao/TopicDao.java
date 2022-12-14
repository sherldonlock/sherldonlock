package com.nicechat.dao;

import com.nicechat.pojo.Topic;

import java.util.List;

public interface TopicDao {

    int addTopic(Topic topic);

    String getTopicIdByTopicName(String topicName);

    List<Topic> getTopicList();

    List<Topic> getHotTopicsTopTen();

    int deleteTopicByTopicId(String topicId);
}
