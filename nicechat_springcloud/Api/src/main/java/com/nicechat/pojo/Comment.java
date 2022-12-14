package com.nicechat.pojo;

import java.io.Serializable;

public class Comment implements Serializable {

    private String commentId;

    private String userId;

    private String avatarUrl;

    private String userName;

    private String comment;

    private String sendTime;

    private String schoolName;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId='" + commentId + '\'' +
                ", userId='" + userId + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", comment='" + comment + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
