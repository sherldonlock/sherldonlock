package com.nicechat.pojo;

import java.io.Serializable;

public class Topic implements Serializable {

    private String id;

    private String topicName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id='" + id + '\'' +
                ", topicName='" + topicName + '\'' +
                '}';
    }
}
