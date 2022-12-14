package com.nicechat.controller;

import cn.hutool.core.date.DateUtil;
import com.nicechat.pojo.User;
import com.nicechat.service.SystemService;
import com.nicechat.utils.CommonResult;
import com.nicechat.utils.IDUtil;
import com.nicechat.utils.ShiroMd5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/provider/system")
public class SystemController {

    private static final Logger log = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    SystemService systemService;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/register")
    public CommonResult register(@RequestBody User user) {
        try {
            if ("".equals(user.getBirthday())) {
                user.setBirthday(null);
            }
            if ("1".equals(user.getSex())) {
                user.setAvatarUrl("/avatar/default_avatar/boy.png");
            } else {
                user.setAvatarUrl("/avatar/default_avatar/girl.png");
            }
            //对密码进行MD5+盐值加密
            String newPassword = ShiroMd5Util.EncryptMd5(user.getAccountNum(), user.getPassword());
            user.setPassword(newPassword);
            //生成uuid，日期
            user.setId(IDUtil.genId());
            user.setCreateDate(DateUtil.now());
            int flag = systemService.register(user);
            if (flag > 0) {
                log.info("注册成功，用户名为：{}，账号为：{}", user.getUserName(), user.getAccountNum());
                return CommonResult.success("注册成功");
            } else {
                log.info("注册失败，用户名为：{}，账号为：{}", user.getUserName(), user.getAccountNum());
                return CommonResult.failed("注册失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/login")
    public CommonResult login(@RequestBody User loginUser) {
        try {
            HashMap<String, Object> resultMap = new HashMap<>();
            UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getAccountNum(), loginUser.getPassword());
            log.info("tokenPassword:{}, tokenUserName:{}", token.getPassword(), token.getUsername());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            User user = systemService.getUserInfoByAccountNum(loginUser.getAccountNum());
            resultMap.put("user", user);
            resultMap.put("token", subject.getSession().getId());
            log.info("登录成功，用户名为：{}, 账号为：{}，token为：{}", user.getUserName(), user.getAccountNum(), subject.getSession().getId());
            return CommonResult.success(resultMap,"登录成功");
        } catch (IncorrectCredentialsException e) {
            log.info("登录失败，账号为{}的用户输入的密码有误", loginUser.getAccountNum());
            return CommonResult.failed("密码错误");
        } catch (AuthenticationException e) {
            return CommonResult.failed("该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("登录失败，账号为：{}", loginUser.getAccountNum());
        return CommonResult.failed("登录失败");
    }

    @RequestMapping("/logout")
    public CommonResult logOut() {
        try {
            Subject subject = SecurityUtils.getSubject();
            /*if(!subject.isAuthenticated()){
                return CommonResult.success("未登录，无需登出");
            }*/
            subject.logout();
            log.info("登出");
            return CommonResult.success("登出成功");
        }catch (Exception e){
            log.error("登出异常", e);
            return CommonResult.failed("登出失败");
        }
    }

    @RequestMapping("/checkUserName")
    public CommonResult checkUserName(@RequestParam("userName") String userName) {
        try {
            int status = systemService.checkUserName(userName);
            return CommonResult.success(status, "检查用户名成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/checkAccountNum")
    public CommonResult checkAccountNum(@RequestParam("accountNum") String accountNum) {
        try {
            int status = systemService.checkAccountNum(accountNum);
            return CommonResult.success(status, "检查账号成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
