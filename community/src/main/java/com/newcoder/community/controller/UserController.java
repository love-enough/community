package com.newcoder.community.controller;

import com.newcoder.community.annotation.LoginRequired;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.LikeService;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @LoginRequired
     @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
         return "/site/setting";
     }

     @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
         if(headerImage == null) {
             model.addAttribute("error", "您还没选择图片!");
             return "/site/setting";
         }

         String fileName = headerImage.getOriginalFilename();
         String suffix = fileName.substring(fileName.lastIndexOf("."));
         if(StringUtils.isBlank(suffix)) {
             model.addAttribute("error", "文件格式不正确!");
             return "/site/setting";
         }
         //生成随机文件名
         fileName = CommunityUtil.generateUUID() + suffix;
         File dest = new File(uploadPath + "/" + fileName);
         try {
             headerImage.transferTo(dest);
         } catch (IOException e) {
             logger.error("上传文件失败！" + e.getMessage());
             throw new RuntimeException("上传文件失败,服务器异常!",e);
         }

         //更新当前用户的头像的路径
         User user = hostHolder.getUser();
         String headerUrl = domain + contextPath + "/user/header/" + fileName;
         userService.updateHeader(user.getId(), headerUrl);
         return "redirect:/index";
     }

     @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
         fileName = uploadPath + "/" + fileName;
         String suffix = fileName.substring(fileName.lastIndexOf("."));
         response.setContentType("image/" + suffix);
         try (
                 FileInputStream fis = new FileInputStream(fileName);
                 OutputStream os = response.getOutputStream();
         ){
             byte[] buffer = new byte[1024];
             int b = 0;
             while ((b = fis.read(buffer)) != -1) {
                 os.write(buffer,0,b);
             }
         } catch (IOException e) {
             logger.error("读取图像失败: " +e.getMessage());
         }

     }

     @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if(user == null){
            throw new RuntimeException("该用户不存在!");
        }
        //用户
         model.addAttribute("user", user);
        //点赞数量
         int likeCount = likeService.findUserLikeCount(userId);
         model.addAttribute("likeCount", likeCount);
         return "/site/profile";
     }


}

