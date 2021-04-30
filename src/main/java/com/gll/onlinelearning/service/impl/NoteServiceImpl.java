package com.gll.onlinelearning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gll.onlinelearning.common.SystemConstant;
import com.gll.onlinelearning.entity.Note;
import com.gll.onlinelearning.mapper.NoteMapper;
import com.gll.onlinelearning.service.NoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author gll
 * @since 2021-04-04
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Override
    public IPage<Note> getPageOfNote(Integer pageIndex) {
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create"); // 根据创建时间倒叙排序，即最新的笔记展示在最前面
        IPage<Note> notePage = new Page<>(pageIndex, SystemConstant.PAGE_SIZE);
        return baseMapper.selectPage(notePage, queryWrapper);
    }
}
