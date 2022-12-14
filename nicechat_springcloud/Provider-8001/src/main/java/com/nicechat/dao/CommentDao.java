package com.nicechat.dao;

import com.nicechat.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {

    int addComment(Comment comment);

    int addDynamicComment(@Param("dynamicId") String dynamicId, @Param("commentId") String commentId);

    int addUserComment(@Param("userId") String userId, @Param("commentId") String commentId);

    List<Comment> getCommentList(String dynamicId);
}
