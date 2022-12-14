package com.nicechat.controller;

import com.nicechat.service.FeignClientService;
import com.nicechat.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/chat")
public class ChatController {

    @Autowired
    FeignClientService feignClientService;

    @RequestMapping("/chatWith")
    public CommonResult chatWith(@RequestParam("loginUserId") String loginUserId,
                                 @RequestParam("userId") String userId) {
        return feignClientService.chatWith(loginUserId, userId);
    }

    @RequestMapping("/getUserList")
    public CommonResult getUserList(@RequestParam("loginUserId") String loginUserId) {
        return feignClientService.getUserList(loginUserId);
    }

    @RequestMapping("/getDialog")
    public CommonResult getDialog(@RequestParam("loginUserId") String loginUserId,
                                  @RequestParam("userId") String userId) {
        return feignClientService.getDialog(loginUserId, userId);
    }

    @RequestMapping("/updateReadFlag")
    public CommonResult updateReadFlag(@RequestParam("loginUserId") String loginUserId,
                                  @RequestParam("userId") String userId) {
        return feignClientService.updateReadFlag(loginUserId, userId);
    }
}
