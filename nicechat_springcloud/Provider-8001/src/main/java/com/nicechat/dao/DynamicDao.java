package com.nicechat.dao;

import com.nicechat.pojo.Dynamic;
import com.nicechat.pojo.Image;
import com.nicechat.pojo.Topic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicDao {

    int addDynamic(Dynamic dynamic);

    int addDynamicImages(@Param("dynamicId") String dynamicId, @Param("imageId") String imageId);

    int addDynamicTopic(@Param("dynamicId") String dynamicId, @Param("topicId") String topicId);

    int addUserDynamic(@Param("userId") String userId, @Param("dynamicId") String dynamicId);

    List<Dynamic> getDynamicList(@Param("schoolName") String schoolName,
                                 @Param("topicName") String topicName,
                                 @Param("userName") String userName,
                                 @Param("focusUserIds") List<String> focusUserIds);

    int addCollect(@Param("dynamicId") String dynamicId,
                   @Param("loginUserId") String loginUserId,
                   @Param("createTime") String createTime);

    int deleteCollect(@Param("dynamicId") String dynamicId, @Param("loginUserId") String loginUserId);

    int addLike(@Param("dynamicId") String dynamicId,
                @Param("loginUserId") String loginUserId,
                @Param("createTime") String createTime);

    int deleteLike(@Param("dynamicId") String dynamicId, @Param("loginUserId") String loginUserId);

    List<String> getCollectListByUserId(String userId);

    List<String> getLikeListByUserId(String userId);

    int deleteUserDynamicCommentCollectLikeByDynamicId(String dynamicId);

    List<String> getImageIdsByDynamicId(String dynamicId);

    int deleteDynamicImageByDynamicId(String dynamicId);

    List<String> getTopicIdsByDynamicId(String dynamicId);

    int deleteDynamicTopicByDynamicIdAndTopicId(@Param("dynamicId") String dynamicId,
                                                @Param("topicId") String topicId);

    int countDynamicTopicByTopicId(String topicId);

    int deleteDynamicByDynamicId(String dynamicId);

}
