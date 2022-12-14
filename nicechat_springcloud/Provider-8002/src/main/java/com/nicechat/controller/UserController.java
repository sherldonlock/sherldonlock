package com.nicechat.controller;

import com.nicechat.pojo.Dynamic;
import com.nicechat.pojo.User;
import com.nicechat.service.UserService;
import com.nicechat.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/provider/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    //头像上传位置
    @Value("${web.uploadPath}")
    private String uploadPath;

    @Autowired
    UserService userService;

    @RequestMapping("/uploadAvatar")
    public CommonResult uploadAvatar(@RequestPart("avatar") MultipartFile uploadFile,
                                     @RequestParam("id") String userId){
        try {
            if (uploadFile == null){
                return CommonResult.failed("上传失败");
            } else {
                String uploadAvatarPath = uploadPath + "/avatar";
                //判断，该路径是否存在
                File file = new File(uploadAvatarPath);
                if (!file.exists()) {
                    //创建该文件夹
                    file.mkdirs();
                }
                //获取上传文件的名称
                String filename = uploadFile.getOriginalFilename();
                //把文件的名称设置唯一值，uuid
                String uuid = UUID.randomUUID().toString().replace("-", "");
                filename = uuid + "_" + filename;
                //完成文件上传
                uploadFile.transferTo(new File(uploadAvatarPath, filename));
                String avatarUrl = "/avatar/" + filename;
                int status = userService.uploadAvatar(userId, avatarUrl);
                if (status > 0) {
                    return CommonResult.success("上传成功");
                } else {
                    return CommonResult.failed("上传失败");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getUserInfo")
    public CommonResult getUserInfo(@RequestParam("userId") String userId) {
        try {
            User userInfo = userService.getUserInfo(userId);
            return CommonResult.success(userInfo,"查询用户详情成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getDynamicListByUserId")
    public CommonResult getDynamicListByUserId(@RequestParam("userId") String userId) {
        try {
            List<Dynamic> dynamicList = userService.getDynamicListByUserId(userId);
            return CommonResult.success(dynamicList,"查询动态列表成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getFocusList")
    public CommonResult getFocusList(@RequestParam("userId") String userId) {
        try {
            List<User> focusList = userService.getFocusList(userId);
            return CommonResult.success(focusList, "查询关注列表成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getFansList")
    public CommonResult getFansList(@RequestParam("userId") String userId) {
        try {
            List<User> fansList = userService.getFansList(userId);
            return CommonResult.success(fansList, "查询粉丝列表成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getMyLikeList")
    public CommonResult getMyLikeList(@RequestParam("userId") String userId) {
        try {
            List<Dynamic> myLikeList = userService.getMyLikeList(userId);
            return CommonResult.success(myLikeList, "查询我的点赞列表成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getMyCollectList")
    public CommonResult getMyCollectList(@RequestParam("userId") String userId) {
        try {
            List<Dynamic> myCollectList = userService.getMyCollectList(userId);
            return CommonResult.success(myCollectList, "查询我的收藏列表成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getMyFocusIdList")
    public CommonResult getMyFocusIdList(@RequestParam("userId") String userId) {
        try {
            List<String> myFocusIdList = userService.getMyFocusIdList(userId);
            return CommonResult.success(myFocusIdList, "查询我关注的Id列表成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/cancelFocus")
    public CommonResult cancelFocus(@RequestParam("loginUserId") String loginUserId,
                                    @RequestParam("userId") String userId) {
        try {
            int status = userService.cancelFocus(loginUserId, userId);
            return CommonResult.success("取消关注成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/Focus")
    public CommonResult Focus(@RequestParam("loginUserId") String loginUserId,
                              @RequestParam("userId") String userId) {
        try {
            int status = userService.focus(loginUserId, userId);
            return CommonResult.success("关注成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/updateUserInfo")
    public CommonResult updateUserInfo(@RequestBody User user) {
        try {
            if ("".equals(user.getBirthday())) {
                user.setBirthday(null);
            }
            User currentUser = userService.updateUserInfo(user);
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("currentUser", currentUser);
            return CommonResult.success(resultMap, "修改个人信息成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
