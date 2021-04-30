package com.gll.onlinelearning.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gll.onlinelearning.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author gll
 * @since 2021-04-04
 */
public interface NoteService extends IService<Note> {
    public IPage<Note> getPageOfNote(Integer pageIndex);

}
