package com.gll.onlinelearning.controller;


import com.gll.onlinelearning.common.GlobalException;
import com.gll.onlinelearning.common.HostHolder;
import com.gll.onlinelearning.entity.User;
import com.gll.onlinelearning.entity.vo.UpdatePasswordRequestVO;
import com.gll.onlinelearning.service.UserService;
import com.gll.onlinelearning.utils.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gll
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final static List<String> imgExtends = Arrays.asList(".jpg", ".png", ".jpeg", ".gif");


    @Value("${onlinelearning.path.upload.img}")
    private String imgUploadPath;

    @Value("${onlinelearning.path.domain}")
    private String domain;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private UserService userService;

    /**
     * 更新头像
     */
    @PostMapping("/updateAvatar")
    public String uploadHeader(MultipartFile file) {
        String fileName;
        if (file == null || (fileName = file.getOriginalFilename()) == null) {
            return SystemUtil.getJSONString(2013, "您还没有选择图片！");
        }
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix) || !imgExtends.contains(suffix.toLowerCase())) {
            return SystemUtil.getJSONString(2013, "图片格式不正确！");
        }
        // 生成随机文件名
        String newFileName = SystemUtil.generateUUID() + suffix;
        // 构建上传文件的存放 "文件夹" 路径
        File fileDir = new File(imgUploadPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        // 确定文件存放的路径
        File dest = new File(fileDir.getAbsolutePath() + "/" + newFileName);
        try {
            // 存储文件
            file.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传图片失败: " + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常!", e);
        }
        // 更新当前用户的头像的路径(web访问路径)
        // http://localhost:8006/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + "/user/header/" + newFileName;
        userService.updateHeader(user.getId(), headerUrl);
        return SystemUtil.getJSONString(200, "上传成功！", new HashMap<String, Object>() {{
            put("imgUrl", headerUrl);
        }});
    }

    /**
     * 获取用户头像
     */
    @GetMapping("/header/{fileName}")
    public void getUserHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = imgUploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                // java 7 写在这里会自动添加finally代码块来关闭流
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream()
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        }
    }

    /**
     * 用户主页，可以查看别人的个人信息
     */
    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new GlobalException(2007, "该用户不存在！");
        }
        Map<String, Object> res = new HashMap<>();
        //这里需要处理一下user，让其不显示某些字段值
        res.put("user", SystemUtil.handleUser(user));
        return SystemUtil.getJSONString(200, "", res);
    }

    /**
     * 修改密码
     */
    @PostMapping("/password")
    public String updatePassword(@RequestBody UpdatePasswordRequestVO requestVO,
                                 @CookieValue("ticket") String ticket) {
        String errMsg = userService.updatePassword(requestVO, hostHolder.getUser());
        if (errMsg.equals("")) {
            userService.logout(ticket);
            //修改密码成功后要前端要重新登录
            return SystemUtil.getJSONString(200, "修改成功，即将退出重新登录！");
        }
        return SystemUtil.getJSONString(2014, errMsg);
    }

    /**
     * 修改用户它信息（除了密码和头像）
     * 其实也就只修改3个字段，邮箱、手机、性别
     */
    @PostMapping("/updateProfile")
    public String updateProfile(@RequestBody User user) {
        boolean isOk = userService.updateProfile(user, hostHolder.getUser().getId());
        int code = isOk ? 200 : 2100;
        String msg = isOk ? "修改成功！" : "修改失败！";
        return SystemUtil.getJSONString(code, msg);
    }
}

