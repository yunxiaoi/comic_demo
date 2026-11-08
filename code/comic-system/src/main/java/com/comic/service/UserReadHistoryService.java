// UserReadHistoryService.java
package com.comic.service;

import com.comic.entity.UserReadHistory;
import java.util.List;

public interface UserReadHistoryService {
    void saveOrUpdate(Integer userId, Integer comicId, Integer chapterId);
    List<UserReadHistory> getByUserId(Integer userId);
    void clearByUserId(Integer userId);
}