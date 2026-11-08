// ForumPostService.java
package com.comic.service;

import com.comic.entity.ForumPost;
import java.util.List;
import java.util.Map;

public interface ForumPostService {
    ForumPost getById(Integer id);
    void save(ForumPost post);
    void update(ForumPost post);
    void deleteById(Integer id);
    void updateStatus(Integer id, Integer status);
    void incrementViews(Integer id);
    List<ForumPost> getByStatus(Integer status);
    List<ForumPost> getAll();
    List<ForumPost> getByUserId(Integer userId);
    Map<String, Object> getApprovedPosts(int page, int size);
    // 获取待审核帖子（分页）
    Map<String, Object> getPendingPosts(int page, int size);
    // 统计帖子总数
    int count();
}