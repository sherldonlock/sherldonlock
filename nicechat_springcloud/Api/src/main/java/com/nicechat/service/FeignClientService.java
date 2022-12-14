package com.nicechat.service;

import com.nicechat.pojo.User;
import com.nicechat.service.config.FeignConfig;
import com.nicechat.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@FeignClient(value = "Nicechat-Provider", configuration = FeignConfig.class)
public interface FeignClientService {

    @RequestMapping("/provider/system/register")
    CommonResult register(@RequestBody User user);

    @RequestMapping("/provider/system/login")
    CommonResult login(@RequestBody User loginUser);

    @RequestMapping("/provider/system/logout")
    CommonResult logOut();

    @RequestMapping(value = "/provider/user/uploadAvatar",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult uploadAvatar(@RequestPart("avatar") MultipartFile uploadFile,
                              @RequestParam("id") String userId);

    @RequestMapping(value = "/provider/index/uploadImages",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult uploadImages(@RequestPart("images") MultipartFile uploadFile, @RequestParam("dynamicId") String dynamicId);

    @RequestMapping("/provider/index/sendDynamic")
    CommonResult sendDynamic(@RequestParam("userId") String userId, @RequestParam("content") String content,
                            @RequestParam("tags") String tags);

    @RequestMapping("/provider/index/getDynamicList")
    CommonResult getDynamicList(@RequestParam(value = "schoolName", required = false) String schoolName,
                                @RequestParam(value = "userId", required = false) String userId,
                                @RequestParam(value = "topicName", required = false) String topicName,
                                @RequestParam(value = "userName", required = false) String userName);

    @RequestMapping("/provider/index/getTopicList")
    CommonResult getTopicList();

    @RequestMapping("/provider/index/getUserNameList")
    CommonResult getUserNameList();

    @RequestMapping("/provider/index/getHotTopicsTopTen")
    CommonResult getHotTopicsTopTen();

    @RequestMapping("/provider/index/getRecommendList")
    CommonResult getRecommendList(@RequestParam("loginUserId") String loginUserId,
                                  @RequestParam("schoolCode") String schoolCode);

    @RequestMapping("/provider/user/getUserInfo")
    CommonResult getUserInfo(@RequestParam("userId") String userId);

    @RequestMapping("/provider/user/getDynamicListByUserId")
    CommonResult getDynamicListByUserId(@RequestParam("userId") String userId);

    @RequestMapping("/provider/index/sendComment")
    CommonResult sendComment(@RequestParam("comment") String comment,
                             @RequestParam("dynamicId") String dynamicId,
                             @RequestParam("userId") String userId);

    @RequestMapping("/provider/index/getCommentList")
    CommonResult getCommentList(@RequestParam("dynamicId") String dynamicId);

    @RequestMapping("/provider/index/setCollectDynamic")
    CommonResult setCollectDynamic(@RequestParam("dynamicId") String dynamicId,
                                   @RequestParam("loginUserId") String loginUserId);

    @RequestMapping("/provider/index/cancelCollectDynamic")
    CommonResult cancelCollectDynamic(@RequestParam("dynamicId") String dynamicId,
                                      @RequestParam("loginUserId") String loginUserId);

    @RequestMapping("/provider/index/setLikeDynamic")
    CommonResult setLikeDynamic(@RequestParam("dynamicId") String dynamicId,
                                @RequestParam("loginUserId") String loginUserId);

    @RequestMapping("/provider/index/cancelLikeDynamic")
    CommonResult cancelLikeDynamic(@RequestParam("dynamicId") String dynamicId,
                                   @RequestParam("loginUserId") String loginUserId);

    @RequestMapping("/provider/index/getMyCollectIdList")
    CommonResult getMyCollectIdList(@RequestParam("userId") String loginUserId);

    @RequestMapping("/provider/index/getMyLikeIdList")
    CommonResult getMyLikeIdList(@RequestParam("userId") String loginUserId);

    @RequestMapping("/provider/user/getFocusList")
    CommonResult getFocusList(@RequestParam("userId") String userId);

    @RequestMapping("/provider/user/getFansList")
    CommonResult getFansList(@RequestParam("userId") String userId);

    @RequestMapping("/provider/user/getMyLikeList")
    CommonResult getMyLikeList(@RequestParam("userId") String userId);

    @RequestMapping("/provider/user/getMyCollectList")
    CommonResult getMyCollectList(@RequestParam("userId") String userId);

    @RequestMapping("/provider/user/getMyFocusIdList")
    CommonResult getMyFocusIdList(@RequestParam("userId") String userId);

    @RequestMapping("/provider/user/cancelFocus")
    CommonResult cancelFocus(@RequestParam("loginUserId") String loginUserId,
                             @RequestParam("userId") String userId);

    @RequestMapping("/provider/user/Focus")
    CommonResult Focus(@RequestParam("loginUserId") String loginUserId,
                       @RequestParam("userId") String userId);

    @RequestMapping("/provider/user/updateUserInfo")
    CommonResult updateUserInfo(@RequestBody User user);

    @RequestMapping("/provider/index/deleteDynamic")
    CommonResult deleteDynamic(@RequestParam("dynamicId") String dynamicId);

    @RequestMapping("/provider/system/checkUserName")
    CommonResult checkUserName(@RequestParam("userName") String userName);

    @RequestMapping("/provider/system/checkAccountNum")
    CommonResult checkAccountNum(@RequestParam("accountNum") String accountNum);

    @RequestMapping("/provider/chat/chatWith")
    CommonResult chatWith(@RequestParam("loginUserId") String loginUserId,
                                 @RequestParam("userId") String userId);

    @RequestMapping("/provider/chat/getUserList")
    CommonResult getUserList(@RequestParam("loginUserId") String loginUserId);

    @RequestMapping("/provider/chat/getDialog")
    CommonResult getDialog(@RequestParam("loginUserId") String loginUserId,
                           @RequestParam("userId") String userId);

    @RequestMapping("/provider/chat/updateReadFlag")
    CommonResult updateReadFlag(@RequestParam("loginUserId") String loginUserId,
                           @RequestParam("userId") String userId);
}
