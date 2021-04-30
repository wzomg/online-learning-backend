package com.gll.onlinelearning.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gll.onlinelearning.common.GlobalException;
import com.gll.onlinelearning.entity.Subject;
import com.gll.onlinelearning.entity.excel.SubjectData;
import com.gll.onlinelearning.service.SubjectService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private static final Logger logger = LoggerFactory.getLogger(SubjectExcelListener.class);


    //因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其它对象
    //不能实现数据库操作
    public SubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel内容，一行一行进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GlobalException(2222, "文件数据为空");
        }

        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断一级分类是否重复
        Subject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null) { //若没有相同一级分类，则添加
            existOneSubject = new Subject();
            existOneSubject.setParentId(0);
            existOneSubject.setTitle(subjectData.getOneSubjectName());//一级分类名称
            //不用添加简介和学习链接
            subjectService.save(existOneSubject);
        }

        //获取一级分类id值
        Integer pid = existOneSubject.getId();

        //添加二级分类
        //判断二级分类是否重复
        Subject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (Strings.isNotBlank(subjectData.getTwoSubjectName())) { //判断一下二级标题是否有值，因为可能只含有一级标题
            //更新和保存需要单独设置，因为保存会生成id，更新可以直接获取id
            if (existTwoSubject == null) {
                existTwoSubject = new Subject();
                existTwoSubject.setParentId(pid);
                existTwoSubject.setTitle(subjectData.getTwoSubjectName());//二级分类名称
                existTwoSubject.setDescription(subjectData.getDescription());//简介
                existTwoSubject.setLink(subjectData.getLink());//学习链接
                subjectService.save(existTwoSubject);
            } else {
                //如果二级分类存在，就更新其它字段：简介和学习链接
                logger.info("SubjectExcelListener -> invoke & update，existTwoSubject：{}", existTwoSubject);
                existTwoSubject.setDescription(subjectData.getDescription());//简介
                existTwoSubject.setLink(subjectData.getLink());//学习链接
                subjectService.updateById(existTwoSubject);
            }
        }
    }

    //判断一级分类不能重复添加
    private Subject existOneSubject(SubjectService subjectService, String name) {
        return subjectService.existSubject(name, 0);
    }

    //判断二级分类不能重复添加
    private Subject existTwoSubject(SubjectService subjectService, String name, Integer pid) {
        return subjectService.existSubject(name, pid);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
