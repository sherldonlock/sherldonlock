package com.nicechat.controller;

import com.nicechat.pojo.User;
import com.nicechat.service.FeignClientService;
import com.nicechat.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/consumer/system")
public class SystemController {

    private static final Logger log = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    FeignClientService feignClientService;

    @RequestMapping("/register")
    public CommonResult register(User user) {
        return feignClientService.register(user);
    }

    @RequestMapping("/login")
    public CommonResult login(User loginUser) {
        return feignClientService.login(loginUser);
    }

    @RequestMapping("/logout")
    public CommonResult logOut() {
        return feignClientService.logOut();
    }

    @RequestMapping("/checkUserName")
    public CommonResult checkUserName(@RequestParam("userName") String userName) {
        return feignClientService.checkUserName(userName);
    }

    @RequestMapping("/checkAccountNum")
    public CommonResult checkAccountNum(@RequestParam("accountNum") String accountNum) {
        return feignClientService.checkAccountNum(accountNum);
    }

}
