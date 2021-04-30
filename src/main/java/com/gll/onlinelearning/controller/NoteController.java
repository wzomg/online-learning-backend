package com.gll.onlinelearning.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.onlinelearning.common.HostHolder;
import com.gll.onlinelearning.entity.Note;
import com.gll.onlinelearning.entity.User;
import com.gll.onlinelearning.service.NoteService;
import com.gll.onlinelearning.utils.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author gll
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);


    @Value("${onlinelearning.path.upload.file}")
    private String fileUploadPath;

    @Value("${onlinelearning.path.domain}")
    private String domain;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private NoteService noteService;

    /**
     * 分页查询出所有用户上传的笔记
     */
    @GetMapping("/all/{pageIndex}")
    public String getPageOfNote(@PathVariable("pageIndex") Integer pageIndex) {
        IPage<Note> list = noteService.getPageOfNote(pageIndex);
        return SystemUtil.getJSONString(200, "", new HashMap<String, Object>() {{
            put("total", list.getTotal());
            put("noteList", list.getRecords());
        }});
    }

    /**
     * 上传笔记
     */
    @PostMapping("/add")
    public String uploadHeader(MultipartFile file) {
        String fileName;
        if (file == null || (fileName = file.getOriginalFilename()) == null) {
            return SystemUtil.getJSONString(2013, "您还没有选择文件！");
        }
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            return SystemUtil.getJSONString(2013, "文件格式不正确！");
        }
        // 生成随机文件名
        String newFileName = SystemUtil.generateUUID() + suffix;
        // 构建上传文件的存放 "文件夹" 路径
        File fileDir = new File(fileUploadPath);
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
            logger.error("上传文件失败: " + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常!", e);
        }
        // 笔记下载路径
        // http://localhost:8006/note/download/xxxxx.pdf
        User user = hostHolder.getUser();
        String downloadUrl = domain + "/note/download/" + newFileName;
        Note note = new Note();
        note.setUid(user.getId());
        note.setUsername(user.getUsername());
        note.setFilename(fileName);
        note.setDownloadUrl(downloadUrl);
        noteService.save(note);
        return SystemUtil.getJSONString(200, "上传成功！");
    }


    /**
     * 下载笔记
     */
    @GetMapping("/download/{fileId}")
    public void downloadNote(@PathVariable("fileId") String fileId, String filename,
                             HttpServletResponse resp) {
        String filePath = fileUploadPath + "/" + fileId;
        try (
                // java 7 写在这里会自动添加finally代码块来关闭流
                FileInputStream fis = new FileInputStream(filePath);
                OutputStream os = resp.getOutputStream()
        ) {
            // 服务器存放路径
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/octet-stream"); //告诉浏览器输出内容为流
            resp.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (Exception e) {
            logger.error("读取文件失败: " + e.getMessage());
        }
    }

    /**
     * 删除笔记
     */
    @DeleteMapping("/delete/{id}")
    public String delNote(@PathVariable("id") Integer id) {
        boolean isOk = noteService.removeById(id);
        int code = isOk ? 200 : 2400;
        String msg = isOk ? "删除成功！" : "删除失败！";
        return SystemUtil.getJSONString(code, msg);
    }
}

