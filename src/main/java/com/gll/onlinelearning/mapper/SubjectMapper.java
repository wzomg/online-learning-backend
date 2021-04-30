package com.gll.onlinelearning.mapper;

import com.gll.onlinelearning.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author gll
 * @since 2021-04-04
 */
public interface SubjectMapper extends BaseMapper<Subject> {
    List<Subject> getAllSubject();
    Subject existSubject(String title, Integer parentId);
}
