package com.nicechat.pojo;

import java.io.Serializable;
import java.util.List;

public class Dynamic implements Serializable {

    private String id;

    private String avatarUrl;

    private String userId;

    private String userName;

    private String postTime;

    private String schoolName;

    private String content;

    private List<Image> imgList;

    private List<Topic> tagList;

    private int collectCount;

    private int commentCount;

    private int likeCount;

    private List<Comment> commentList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Image> getImgList() {
        return imgList;
    }

    public void setImgList(List<Image> imgList) {
        this.imgList = imgList;
    }

    public List<Topic> getTagList() {
        return tagList;
    }

    public void setTagList(List<Topic> tagList) {
        this.tagList = tagList;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "id='" + id + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", postTime='" + postTime + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", content='" + content + '\'' +
                ", imgList=" + imgList +
                ", tagList=" + tagList +
                ", collectCount=" + collectCount +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                ", commentList=" + commentList +
                '}';
    }
}
