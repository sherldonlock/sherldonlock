package com.nicechat.controller;

import com.nicechat.pojo.Comment;
import com.nicechat.pojo.Dynamic;
import com.nicechat.pojo.Topic;
import com.nicechat.pojo.User;
import com.nicechat.service.DynamicService;
import com.nicechat.service.TopicService;
import com.nicechat.service.UserService;
import com.nicechat.utils.CommonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;
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
@RequestMapping("/provider/index")
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Value("${web.uploadPath}")
    private String uploadPath;

    @Autowired
    DynamicService dynamicService;

    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;

    @RequestMapping("/uploadImages")
    public CommonResult uploadImages(@RequestPart("images") MultipartFile uploadFile, @RequestParam("dynamicId") String dynamicId){
        try {
            if (uploadFile == null){
                return CommonResult.failed("上传失败");
            } else {
                String uploadImagesPath = uploadPath + "/images";
                //判断，该路径是否存在
                File file = new File(uploadImagesPath);
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
                uploadFile.transferTo(new File(uploadImagesPath, filename));
                String imageUrl = "/images/" + filename;
                int status = dynamicService.uploadDynamicImages(dynamicId, imageUrl);
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

    @RequestMapping("/sendDynamic")
    public CommonResult sendDynamic(@RequestParam("userId") String userId, @RequestParam("content") String content
                                    ,@RequestParam("tags") String tags) {
        try {
            String dynamicId = dynamicService.sendDynamic(userId, content, tags);
            HashMap<String, String> resultMap = new HashMap<>();
            resultMap.put("dynamicId", dynamicId);
            return CommonResult.success(resultMap,"发布动态成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/getDynamicList")
    public CommonResult getDynamicList(@RequestParam(value = "schoolName", required = false) String schoolName,
                                       @RequestParam(value = "userId", required = false) String userId,
                                       @RequestParam(value = "topicName", required = false) String topicName,
                                       @RequestParam(value = "userName", required = false) String userName) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Object principal = subject.getPrincipal();
            System.out.println(principal);
            List<Dynamic> dynamicList = dynamicService.getDynamicList(schoolName, userId, topicName, userName);
            return CommonResult.success(dynamicList,"查询动态列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("查询动态列表异常");
        }
    }

    @RequestMapping("/getTopicList")
    public CommonResult getTopicList() {
        try {
            List<Topic> topicList = topicService.getTopicList();
            return CommonResult.success(topicList, "查询话题列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("查询话题列表异常");
        }
    }

    @RequestMapping("/getUserNameList")
    public CommonResult getUserNameList() {
        try {
            List<User> userNameList = userService.getUserNameList();
            return CommonResult.success(userNameList, "查询用户列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("查询用户列表异常");
        }
    }

    @RequestMapping("/getHotTopicsTopTen")
    public CommonResult getHotTopicsTopTen() {
        try {
            List<Topic> hotTopicsTopTen = topicService.getHotTopicsTopTen();
            return CommonResult.success(hotTopicsTopTen, "查询热点话题列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("查询热点话题列表异常");
        }
    }

    @RequestMapping("/getRecommendList")
    public CommonResult getRecommendList(@RequestParam("loginUserId") String loginUserId,
                                  @RequestParam("schoolCode") String schoolCode) {
        try {
            List<User> recommendList = userService.getRecommendList(loginUserId, schoolCode);
            return CommonResult.success(recommendList, "查询推荐列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("查询推荐列表异常");
        }
    }

    @RequestMapping("/sendComment")
    public CommonResult sendComment(@RequestParam("comment") String comment,
                             @RequestParam("dynamicId") String dynamicId,
                             @RequestParam("userId") String userId) {
        try {
            int status = dynamicService.sendComment(comment, dynamicId, userId);
            if (status > 0) {
                return CommonResult.success("评论成功");
            } else {
                return CommonResult.failed("评论失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("评论异常");
        }
    }

    @RequestMapping("/getCommentList")
    public CommonResult getCommentList(@RequestParam("dynamicId") String dynamicId) {
        try {
            List<Comment> commentList = dynamicService.getCommentList(dynamicId);
            return CommonResult.success(commentList, "查询评论列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("查询评论列表异常");
        }
    }

    @RequestMapping("/setCollectDynamic")
    public CommonResult setCollectDynamic(@RequestParam("dynamicId") String dynamicId,
                                          @RequestParam("loginUserId") String loginUserId) {
        try {
            List<String> loginUserCollectDynamicIds = dynamicService.setCollectDynamic(dynamicId, loginUserId);
            return CommonResult.success(loginUserCollectDynamicIds, "收藏动态成功");
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("收藏动态异常");
        }
    }

    @RequestMapping("/cancelCollectDynamic")
    public CommonResult cancelCollectDynamic(@RequestParam("dynamicId") String dynamicId,
                                          @RequestParam("loginUserId") String loginUserId) {
        try {
            List<String> loginUserCollectDynamicIds = dynamicService.cancelCollectDynamic(dynamicId, loginUserId);
            return CommonResult.success(loginUserCollectDynamicIds, "取消收藏动态成功");
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("取消收藏动态异常");
        }
    }

    @RequestMapping("/setLikeDynamic")
    public CommonResult setLikeDynamic(@RequestParam("dynamicId") String dynamicId,
                                          @RequestParam("loginUserId") String loginUserId) {
        try {
            List<String> loginUserLikeDynamicIds = dynamicService.setLikeDynamic(dynamicId, loginUserId);
            return CommonResult.success(loginUserLikeDynamicIds, "点赞动态成功");
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("点赞动态异常");
        }
    }

    @RequestMapping("/cancelLikeDynamic")
    public CommonResult cancelLikeDynamic(@RequestParam("dynamicId") String dynamicId,
                                          @RequestParam("loginUserId") String loginUserId) {
        try {
            List<String> loginUserLikeDynamicIds = dynamicService.cancelLikeDynamic(dynamicId, loginUserId);
            return CommonResult.success(loginUserLikeDynamicIds, "取消点赞动态成功");
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("取消点赞动态异常");
        }
    }

    @RequestMapping("/getMyCollectIdList")
    public CommonResult getMyCollectIdList(@RequestParam("userId") String userId) {
        try {
            List<String> myCollectIdList = dynamicService.getMyCollectIdList(userId);
            return CommonResult.success(myCollectIdList, "查询我收藏的动态Id列表成功");
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("查询我收藏的动态Id列表异常");
        }
    }

    @RequestMapping("/getMyLikeIdList")
    public CommonResult getMyLikeIdList(@RequestParam("userId") String userId) {
        try {
            List<String> myLikeIdList = dynamicService.getMyLikeIdList(userId);
            return CommonResult.success(myLikeIdList, "查询我点赞的动态Id列表成功");
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("查询我点赞的动态Id列表异常");
        }
    }

    @RequestMapping("/deleteDynamic")
    public CommonResult deleteDynamic(@RequestParam("dynamicId") String dynamicId) {
        try {
            dynamicService.deleteDynamic(dynamicId);
            return CommonResult.success("删除动态成功");
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("删除动态异常");
        }
    }
}
