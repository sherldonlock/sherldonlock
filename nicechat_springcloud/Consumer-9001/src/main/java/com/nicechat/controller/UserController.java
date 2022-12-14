package com.nicechat.controller;

import com.nicechat.pojo.User;
import com.nicechat.service.FeignClientService;
import com.nicechat.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/consumer/user")
public class UserController {

    @Autowired
    FeignClientService feignClientService;

    @RequestMapping(value = "/uploadAvatar",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult uploadAvatar(@RequestPart("avatar") MultipartFile uploadFile,
                                     @RequestParam("id") String userId){
        return feignClientService.uploadAvatar(uploadFile, userId);
    }

    @RequestMapping("/getUserInfo")
    public CommonResult getUserInfo(@RequestParam("userId") String userId) {
        return feignClientService.getUserInfo(userId);
    }

    @RequestMapping("/getDynamicListByUserId")
    public CommonResult getDynamicListByUserId(@RequestParam("userId") String userId) {
        return feignClientService.getDynamicListByUserId(userId);
    }

    @RequestMapping("/getFocusList")
    public CommonResult getFocusList(@RequestParam("userId") String userId) {
        return feignClientService.getFocusList(userId);
    }

    @RequestMapping("/getFansList")
    public CommonResult getFansList(@RequestParam("userId") String userId) {
        return feignClientService.getFansList(userId);
    }

    @RequestMapping("/getMyLikeList")
    public CommonResult getMyLikeList(@RequestParam("userId") String userId) {
        return feignClientService.getMyLikeList(userId);
    }

    @RequestMapping("/getMyCollectList")
    public CommonResult getMyCollectList(@RequestParam("userId") String userId) {
        return feignClientService.getMyCollectList(userId);
    }

    @RequestMapping("/getMyFocusIdList")
    public CommonResult getMyFocusIdList(@RequestParam("userId") String userId) {
        return feignClientService.getMyFocusIdList(userId);
    }

    @RequestMapping("/cancelFocus")
    public CommonResult cancelFocus(@RequestParam("loginUserId") String loginUserId,
                                    @RequestParam("userId") String userId) {
        return feignClientService.cancelFocus(loginUserId, userId);
    }

    @RequestMapping("/Focus")
    public CommonResult Focus(@RequestParam("loginUserId") String loginUserId,
                              @RequestParam("userId") String userId) {
        return feignClientService.Focus(loginUserId, userId);
    }

    @RequestMapping("/updateUserInfo")
    public CommonResult updateUserInfo(User user) {
        return feignClientService.updateUserInfo(user);
    }
}
