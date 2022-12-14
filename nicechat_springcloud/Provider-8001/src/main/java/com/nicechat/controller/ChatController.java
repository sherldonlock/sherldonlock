package com.nicechat.controller;

import com.nicechat.pojo.Dialog;
import com.nicechat.pojo.User;
import com.nicechat.service.ChatService;
import com.nicechat.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/provider/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @RequestMapping("/chatWith")
    public CommonResult chatWith(@RequestParam("loginUserId") String loginUserId,
                                @RequestParam("userId") String userId) {
        try {
            int status = chatService.chatWith(loginUserId, userId);
            if (status > 0) {
                return CommonResult.success("私聊成功");
            } else {
                return CommonResult.failed("私聊失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getUserList")
    public CommonResult getUserList(@RequestParam("loginUserId") String loginUserId) {
        try {
            List<User> userList = chatService.getUserList(loginUserId);
            return CommonResult.success(userList, "查询聊天用户列表成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getDialog")
    public CommonResult getDialog(@RequestParam("loginUserId") String loginUserId,
                                  @RequestParam("userId") String userId) {
        try {
            List<Dialog> dialogs = chatService.getDialog(loginUserId, userId);
            return CommonResult.success(dialogs, "查询聊天记录成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/updateReadFlag")
    public CommonResult updateReadFlag(@RequestParam("loginUserId") String loginUserId,
                                  @RequestParam("userId") String userId) {
        try {
            chatService.updateReadFlag(loginUserId, userId);
            return CommonResult.success("更新读取状态成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
