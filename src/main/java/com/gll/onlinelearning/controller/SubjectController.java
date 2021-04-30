package com.gll.onlinelearning.controller;

import com.gll.onlinelearning.entity.Subject;
import com.gll.onlinelearning.service.SubjectService;
import com.gll.onlinelearning.utils.SystemUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author gll
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    /**
     * 获取所有课程列表
     */
    @GetMapping("/all")
    public String getAllSubject() {
        List<Subject> subjectList = subjectService.getAllSubject();
        return SystemUtil.getJSONString(200, "", new HashMap<String, Object>() {{
            put("subjectList", subjectList);
        }});
    }

    /**
     * 读取 execl 数据并插入到数据库中
     */
    @PostMapping("/add")
    public String addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return SystemUtil.getJSONString(200, "上传成功！");
    }
}

