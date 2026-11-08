// CommentServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.Comment;
import com.comic.mapper.CommentMapper;
import com.comic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment getById(Integer id) {
        return commentMapper.findById(id);
    }

    @Override
    public List<Comment> getByTarget(Integer targetType, Integer targetId) {
        return commentMapper.findByTarget(targetType, targetId);
    }

    @Override
    public List<Comment> getByUserId(Integer userId) {
        return commentMapper.findByUserId(userId);
    }

    @Override
    public List<Comment> getAll() {
        return commentMapper.findAll();
    }

    @Override
    public void save(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }
    @Override
    public int count() {
        return commentMapper.countAll();
    }
    @Override
    public Map<String, Object> listAll(int page, int size) {
        int offset = (page - 1) * size;
        List<Comment> list = commentMapper.findPage(offset, size); // 需要添加 Mapper 方法
        int total = commentMapper.countAll(); // 已有方法
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
}