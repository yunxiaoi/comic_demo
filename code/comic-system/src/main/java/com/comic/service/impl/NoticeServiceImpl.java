// NoticeServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.Notice;
import com.comic.mapper.NoticeMapper;
import com.comic.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> getAll() {
        return noticeMapper.findAll();
    }

    @Override
    public Notice getById(Integer id) {
        return noticeMapper.findById(id);
    }

    @Override
    public void save(Notice notice) {
        noticeMapper.insert(notice);
    }

    @Override
    public void update(Notice notice) {
        noticeMapper.update(notice);
    }

    @Override
    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }
}

