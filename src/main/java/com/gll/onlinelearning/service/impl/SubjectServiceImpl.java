package com.gll.onlinelearning.service.impl;

import com.alibaba.excel.EasyExcel;
import com.gll.onlinelearning.entity.Subject;
import com.gll.onlinelearning.entity.excel.SubjectData;
import com.gll.onlinelearning.listener.SubjectExcelListener;
import com.gll.onlinelearning.mapper.SubjectMapper;
import com.gll.onlinelearning.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.onlinelearning.utils.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author gll
 * @since 2021-04-04
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<Subject> getAllSubject() {
        List<Subject> subjectList = getSubjectListCache();
        if (subjectList == null || subjectList.isEmpty()) {
            subjectList = initSubjectListCache();
        }
        return subjectList;
    }

    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
            //清除课程列表缓存
            clearSubjectListCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Subject existSubject(String title, Integer parentId) {
        return baseMapper.existSubject(title, parentId);
    }

    // 1.优先从缓存中取值
    private List<Subject> getSubjectListCache() {
        String redisKey = RedisKeyUtil.getSubjectKey();
        return (List<Subject>) redisTemplate.opsForValue().get(redisKey);
    }

    // 2.取不到时初始化缓存数据
    private List<Subject> initSubjectListCache() {
        List<Subject> allSubject = baseMapper.getAllSubject();
        String redisKey = RedisKeyUtil.getSubjectKey();
        redisTemplate.opsForValue().set(redisKey, allSubject, 3600, TimeUnit.SECONDS); // 存储1小时
        return allSubject;
    }

    // 3.数据变更时清除缓存数据
    private void clearSubjectListCache() {
        String redisKey = RedisKeyUtil.getSubjectKey();
        redisTemplate.delete(redisKey);
    }
}
