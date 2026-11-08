// CommentService.java
package com.comic.service;

import com.comic.entity.Comment;
import java.util.List;
import java.util.Map;

public interface CommentService {
    Comment getById(Integer id);
    List<Comment> getByTarget(Integer targetType, Integer targetId);
    List<Comment> getByUserId(Integer userId);
    List<Comment> getAll();
    void save(Comment comment);
    void deleteById(Integer id);
    // 统计评论总数
    int count();
    Map<String, Object> listAll(int page, int size);
}