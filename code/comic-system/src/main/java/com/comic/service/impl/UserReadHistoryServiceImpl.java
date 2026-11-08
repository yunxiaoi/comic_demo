// UserReadHistoryServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.UserReadHistory;
import com.comic.mapper.UserReadHistoryMapper;
import com.comic.service.UserReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReadHistoryServiceImpl implements UserReadHistoryService {

    @Autowired
    private UserReadHistoryMapper historyMapper;

    @Override
    public void saveOrUpdate(Integer userId, Integer comicId, Integer chapterId) {
        UserReadHistory existing = historyMapper.findByUserAndComic(userId, comicId);
        if (existing == null) {
            historyMapper.insert(userId, comicId, chapterId);
        } else {
            historyMapper.updateReadTime(userId, comicId, chapterId);
        }
    }

    @Override
    public List<UserReadHistory> getByUserId(Integer userId) {
        return historyMapper.findByUserId(userId);
    }

    @Override
    public void clearByUserId(Integer userId) {
        historyMapper.deleteByUserId(userId);
    }
}