package com.nicechat.service;

import com.nicechat.pojo.*;

import java.util.List;

public interface DynamicService {

    String sendDynamic(String userId, String content, String tags);

    int uploadDynamicImages(String dynamicId, String imageUrl);

    List<Dynamic> getDynamicList(String schoolName, String userId, String topicName, String userName);

    int sendComment(String comment, String dynamicId, String userId);

    List<Comment> getCommentList(String dynamicId);

    List<String> setCollectDynamic(String dynamicId, String loginUserId);

    List<String> cancelCollectDynamic(String dynamicId, String loginUserId);

    List<String> setLikeDynamic(String dynamicId, String loginUserId);

    List<String> cancelLikeDynamic(String dynamicId, String loginUserId);

    List<String> getMyCollectIdList(String userId);

    List<String> getMyLikeIdList(String userId);

    void deleteDynamic(String dynamicId);
}
