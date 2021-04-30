package com.gll.onlinelearning.service;

import com.gll.onlinelearning.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gll
 * @since 2021-04-04
 */
public interface SubjectService extends IService<Subject> {
    public List<Subject> getAllSubject();

    public void saveSubject(MultipartFile file, SubjectService subjectService);

    public Subject existSubject(String title, Integer parentId);
}
