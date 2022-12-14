package com.nicechat.pojo;

import java.io.Serializable;

public class User implements Serializable {

    private String id;

    private String accountNum;

    private String password;

    private String userName;

    private String avatarUrl;

    private String sex;

    private String emotion;

    private String birthday;

    private String schoolCode;

    private String schoolName;

    private String introduction;

    private String createDate;

    private String dynamicCount;

    private String focusCount;

    private String fansCount;

    private String unReadCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDynamicCount() {
        return dynamicCount;
    }

    public void setDynamicCount(String dynamicCount) {
        this.dynamicCount = dynamicCount;
    }

    public String getFocusCount() {
        return focusCount;
    }

    public void setFocusCount(String focusCount) {
        this.focusCount = focusCount;
    }

    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }

    public String getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(String unReadCount) {
        this.unReadCount = unReadCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", accountNum='" + accountNum + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", sex='" + sex + '\'' +
                ", emotion='" + emotion + '\'' +
                ", birthday='" + birthday + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", createDate='" + createDate + '\'' +
                ", dynamicCount='" + dynamicCount + '\'' +
                ", focusCount='" + focusCount + '\'' +
                ", fansCount='" + fansCount + '\'' +
                ", unReadCount='" + unReadCount + '\'' +
                '}';
    }
}
