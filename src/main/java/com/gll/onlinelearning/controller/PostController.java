package com.gll.onlinelearning.controller;


import com.gll.onlinelearning.common.HostHolder;
import com.gll.onlinelearning.entity.Post;
import com.gll.onlinelearning.entity.User;
import com.gll.onlinelearning.entity.vo.PageOfPostResultVO;
import com.gll.onlinelearning.service.PostService;
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

/**
 * @author gll
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/post")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final static List<String> imgExtends = Arrays.asList(".jpg", ".png", ".jpeg", ".gif");

    @Value("${onlinelearning.path.upload.img}")
    private String imgUploadPath;

    @Value("${onlinelearning.path.domain}")
    private String domain;

    @Resource
    private PostService postService;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private UserService userService;

    /**
     * 上传图片
     */
    @PostMapping("/imgUpload")
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
            throw new RuntimeException("上传图片失败，服务器发生异常!", e);
        }
        // 讨论区中的图片链接(web访问路径)
        // http://localhost:8006/post/imgLoader/xxx.png
        String headerUrl = domain + "/post/imgLoader/" + newFileName;
        return SystemUtil.getJSONString(200, "上传成功！", new HashMap<String, Object>() {{
            put("imgUrl", headerUrl);
        }});
    }

    /**
     * 获取讨论区上传的图片
     */
    @GetMapping("/imgLoader/{filename}")
    public void getUserHeader(@PathVariable("filename") String filename, HttpServletResponse response) {
        filename = imgUploadPath + "/" + filename;
        String suffix = filename.substring(filename.lastIndexOf("."));
        response.setContentType("image/" + suffix);
        try (
                // java 7 写在这里会自动添加finally代码块来关闭流
                FileInputStream fis = new FileInputStream(filename);
                OutputStream os = response.getOutputStream()
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取图片失败: " + e.getMessage());
        }
    }

    /**
     * 发表帖子，传来2个字段：content 和 pictures（非必须，因为我这边给了默认值）
     */
    @PostMapping("/add")
    public String addPost(@RequestBody Post post) {
        User user = hostHolder.getUser();
        post.setUid(user.getId());
        boolean isOk = postService.save(post);
        int code = isOk ? 200 : 2300;
        String msg = isOk ? "发表成功！" : "发表失败！";
        return SystemUtil.getJSONString(code, msg);
    }

    /**
     * 分页获取帖子列表，按照更新时间倒叙排，只需要传页码，搜索字段非必须
     */
    @GetMapping("/all")
    public String getPageOfPost(Integer pageIndex, String searchContent) {
        List<PageOfPostResultVO> list = postService.getPageOfPost(pageIndex, searchContent);
        int total = postService.getCountOfPostByContent(searchContent);
        return SystemUtil.getJSONString(200, "", new HashMap<String, Object>() {{
            put("total", total);
            put("postList", list);
        }});
    }

    /**
     * 获取某条帖子的信息，只需要传帖子 id
     */
    @GetMapping("/get/{id}")
    public String getPostById(@PathVariable("id") Integer id) {
        Post post = postService.getById(id);
        //再查出当前帖子的用户信息一起传给前端
        User user = userService.getUserById(post.getUid());
        return SystemUtil.getJSONString(200, "", new HashMap<String, Object>() {{
            put("pInfo", post);
            put("uInfo", SystemUtil.handleUser(user));
        }});
    }

    /**
     * 更新某条帖子信息，传帖子id，content，pictures 这3个字段即可
     */
    @PostMapping("/update")
    public String updatePost(@RequestBody Post post) {
        boolean isOk = postService.updatePost(post);
        int code = isOk ? 200 : 2300;
        String msg = isOk ? "更新成功！" : "更新失败！";
        return SystemUtil.getJSONString(code, msg);
    }

    /**
     * 删除帖子，当前用户为登录的用户
     */
    @DeleteMapping("/del/{id}")
    public String delPost(@PathVariable("id") Integer id) {
        boolean isOk = postService.delPostById(id);
        int code = isOk ? 200 : 2300;
        String msg = isOk ? "删除帖子成功！" : "删除帖子失败！";
        return SystemUtil.getJSONString(code, msg);
    }
}

