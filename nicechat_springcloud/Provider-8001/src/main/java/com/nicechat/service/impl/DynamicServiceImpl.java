package com.nicechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.nicechat.dao.*;
import com.nicechat.pojo.*;
import com.nicechat.service.DynamicService;
import com.nicechat.utils.IDUtil;
import cn.hutool.core.date.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Value("${web.uploadPath}")
    private String uploadPath;

    @Autowired
    DynamicDao dynamicDao;

    @Autowired
    TopicDao topicDao;

    @Autowired
    ImageDao imageDao;

    @Autowired
    FansDao fansDao;

    @Autowired
    CommentDao commentDao;

    @Override
    //需要的是读写型事务配置
    @Transactional
    public String sendDynamic(String userId, String content, String tags) {
        try {
            Dynamic dynamic = new Dynamic();
            String dynamicId = IDUtil.genId();
            dynamic.setId(dynamicId);
            dynamic.setContent(content);
            dynamic.setPostTime(DateUtil.now());
            dynamicDao.addDynamic(dynamic);
            List<Topic> topics = JSON.parseArray(tags, Topic.class);
            for (Topic topic : topics) {
                String topicId;
                topicId = topicDao.getTopicIdByTopicName(topic.getTopicName());
                if (topicId == null) {
                    topicId = IDUtil.genId();
                    topic.setId(topicId);
                    topicDao.addTopic(topic);
                } else {
                    topic.setId(topicId);
                }
                dynamicDao.addDynamicTopic(dynamicId, topic.getId());
            }
            dynamicDao.addUserDynamic(userId, dynamicId);
            //制造异常
//            int i = 1 / 0;
            return dynamicId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public int uploadDynamicImages(String dynamicId, String imageUrl) {
        try {
            String imageId = IDUtil.genId();
            Image image = new Image();
            image.setId(imageId);
            image.setImageUrl(imageUrl);
            int status1 = imageDao.addImage(image);
            int status2 = dynamicDao.addDynamicImages(dynamicId, imageId);
            //制造异常
//            int i = 1 / 0;
            if (status1 == 1 && status2 == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dynamic> getDynamicList(String schoolName, String userId, String topicName, String userName) {
        List<String> focusUserIds = null;
        if (userId != null) {
            focusUserIds = fansDao.getFocusUserIdsByLoginUserId(userId);
            if (focusUserIds.isEmpty()) {
                focusUserIds.add("ABC");
            }
        }
        return dynamicDao.getDynamicList(schoolName, topicName, userName, focusUserIds);
    }

    @Override
    @Transactional
    public int sendComment(String content, String dynamicId, String userId) {
        String commentId = IDUtil.genId();
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setComment(content);
        comment.setSendTime(DateUtil.now());
        int status1 = commentDao.addComment(comment);
        int status2 = commentDao.addDynamicComment(dynamicId, commentId);
        int status3 = commentDao.addUserComment(userId, commentId);
        if (status1 == 1 && status2 == 1 && status3 == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<Comment> getCommentList(String dynamicId) {
        return commentDao.getCommentList(dynamicId);
    }

    @Override
    @Transactional
    public List<String> setCollectDynamic(String dynamicId, String loginUserId) {
        dynamicDao.addCollect(dynamicId, loginUserId, DateUtil.now());
        return dynamicDao.getCollectListByUserId(loginUserId);
    }

    @Override
    @Transactional
    public List<String> cancelCollectDynamic(String dynamicId, String loginUserId) {
        dynamicDao.deleteCollect(dynamicId, loginUserId);
        return dynamicDao.getCollectListByUserId(loginUserId);
    }

    @Override
    @Transactional
    public List<String> setLikeDynamic(String dynamicId, String loginUserId) {
        dynamicDao.addLike(dynamicId, loginUserId, DateUtil.now());
        return dynamicDao.getLikeListByUserId(loginUserId);
    }

    @Override
    @Transactional
    public List<String> cancelLikeDynamic(String dynamicId, String loginUserId) {
        dynamicDao.deleteLike(dynamicId, loginUserId);
        return dynamicDao.getLikeListByUserId(loginUserId);
    }

    @Override
    public List<String> getMyCollectIdList(String userId) {
        return dynamicDao.getCollectListByUserId(userId);
    }

    @Override
    public List<String> getMyLikeIdList(String userId) {
        return dynamicDao.getLikeListByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteDynamic(String dynamicId) {
        dynamicDao.deleteUserDynamicCommentCollectLikeByDynamicId(dynamicId);
        List<String> imageIds = dynamicDao.getImageIdsByDynamicId(dynamicId);
        if (!imageIds.isEmpty()) {
            List<Image> images = imageDao.getImageByImageIds(imageIds);
            dynamicDao.deleteDynamicImageByDynamicId(dynamicId);
            imageDao.deleteImageByImageIds(imageIds);
            for (Image image : images) {
                File file = new File(uploadPath + image.getImageUrl());
                if (file.isFile() && file.exists()) {
                    file.delete();
                }
            }
        }
        List<String> topicIds = dynamicDao.getTopicIdsByDynamicId(dynamicId);
        for (String topicId : topicIds) {
            dynamicDao.deleteDynamicTopicByDynamicIdAndTopicId(dynamicId, topicId);
            if (dynamicDao.countDynamicTopicByTopicId(topicId) == 0) {
                topicDao.deleteTopicByTopicId(topicId);
            }
        }
        dynamicDao.deleteDynamicByDynamicId(dynamicId);
    }
}
