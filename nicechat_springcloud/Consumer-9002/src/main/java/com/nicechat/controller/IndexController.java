package com.nicechat.controller;

import com.nicechat.pojo.Topic;
import com.nicechat.pojo.User;
import com.nicechat.service.FeignClientService;
import com.nicechat.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/consumer/index")
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    FeignClientService feignClientService;

    @RequestMapping(value = "/uploadImages",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult uploadImages(@RequestPart("images") MultipartFile uploadFile, @RequestParam("dynamicId") String dynamicId){
        return feignClientService.uploadImages(uploadFile, dynamicId);
    }

    @RequestMapping("/sendDynamic")
    public CommonResult sendDynamic(@RequestParam("userId") String userId, @RequestParam("content") String content,
                                    @RequestParam("tags") String tags) {
        return feignClientService.sendDynamic(userId, content, tags);
    }

    @RequestMapping("/getDynamicList")
    public CommonResult getDynamicList(@RequestParam(value = "schoolName", required = false) String schoolName,
                                       @RequestParam(value = "userId", required = false) String userId,
                                       @RequestParam(value = "topicName", required = false) String topicName,
                                       @RequestParam(value = "userName", required = false) String userName) {
        return feignClientService.getDynamicList(schoolName, userId, topicName, userName);
    }

    @RequestMapping("/getTopicList")
    public CommonResult getTopicList() {
        return feignClientService.getTopicList();
    }

    @RequestMapping("/getUserNameList")
    public CommonResult getUserNameList() {
        return feignClientService.getUserNameList();
    }

    @RequestMapping("/getHotTopicsTopTen")
    public CommonResult getHotTopicsTopTen() {
        return feignClientService.getHotTopicsTopTen();
    }

    @RequestMapping("/getRecommendList")
    public CommonResult getRecommendList(@RequestParam(value = "loginUserId") String loginUserId,
                                         @RequestParam(value = "schoolCode") String schoolCode) {
        return feignClientService.getRecommendList(loginUserId, schoolCode);
    }

    @RequestMapping("/sendComment")
    public CommonResult sendComment(@RequestParam("comment") String comment,
                                    @RequestParam("dynamicId") String dynamicId,
                                    @RequestParam("userId") String userId) {

        return feignClientService.sendComment(comment, dynamicId, userId);
    }

    @RequestMapping("/getCommentList")
    public CommonResult getCommentList(@RequestParam("dynamicId") String dynamicId) {
        return feignClientService.getCommentList(dynamicId);
    }

    @RequestMapping("/setCollectDynamic")
    public CommonResult setCollectDynamic(@RequestParam("dynamicId") String dynamicId,
                                          @RequestParam("loginUserId") String loginUserId) {
        return feignClientService.setCollectDynamic(dynamicId, loginUserId);
    }

    @RequestMapping("/cancelCollectDynamic")
    public CommonResult cancelCollectDynamic(@RequestParam("dynamicId") String dynamicId,
                                             @RequestParam("loginUserId") String loginUserId) {
        return feignClientService.cancelCollectDynamic(dynamicId, loginUserId);
    }

    @RequestMapping("/setLikeDynamic")
    public CommonResult setLikeDynamic(@RequestParam("dynamicId") String dynamicId,
                                       @RequestParam("loginUserId") String loginUserId) {
        return feignClientService.setLikeDynamic(dynamicId, loginUserId);
    }

    @RequestMapping("/cancelLikeDynamic")
    public CommonResult cancelLikeDynamic(@RequestParam("dynamicId") String dynamicId,
                                          @RequestParam("loginUserId") String loginUserId) {
        return feignClientService.cancelLikeDynamic(dynamicId, loginUserId);
    }

    @RequestMapping("/getMyCollectIdList")
    public CommonResult getMyCollectIdList(@RequestParam("userId") String loginUserId) {
        return feignClientService.getMyCollectIdList(loginUserId);
    }

    @RequestMapping("/getMyLikeIdList")
    public CommonResult getMyLikeIdList(@RequestParam("userId") String loginUserId) {
        return feignClientService.getMyLikeIdList(loginUserId);
    }

    @RequestMapping("/deleteDynamic")
    public CommonResult deleteDynamic(@RequestParam("dynamicId") String dynamicId) {
        return feignClientService.deleteDynamic(dynamicId);
    }
}
