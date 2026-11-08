package com.comic.service.impl;

import com.comic.entity.ForumPost;
import com.comic.mapper.ForumPostMapper;
import com.comic.service.ForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForumPostServiceImpl implements ForumPostService {

    @Autowired
    private ForumPostMapper forumPostMapper;

    @Override
    public ForumPost getById(Integer id) {
        return forumPostMapper.findById(id);
    }

    @Override
    public void save(ForumPost post) {
        forumPostMapper.insert(post);
    }

    @Override
    public void update(ForumPost post) {
        forumPostMapper.update(post);
    }

    @Override
    public void deleteById(Integer id) {
        forumPostMapper.deleteById(id);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        forumPostMapper.updateStatus(id, status);
    }

    @Override
    public void incrementViews(Integer id) {
        forumPostMapper.incrementViews(id);
    }

    @Override
    public List<ForumPost> getByStatus(Integer status) {
        return forumPostMapper.findByStatus(status);
    }

    @Override
    public List<ForumPost> getAll() {
        return forumPostMapper.findAll();
    }

    @Override
    public List<ForumPost> getByUserId(Integer userId) {
        return forumPostMapper.findByUserId(userId);
    }

    @Override
    public Map<String, Object> getApprovedPosts(int page, int size) {
        int offset = (page - 1) * size;
        List<ForumPost> list = forumPostMapper.findApproved(null, offset, size); // categoryId传null
        int total = forumPostMapper.countApproved(null);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
    @Override
    public int count() {
        return forumPostMapper.countAll();
    }
    @Override
    public Map<String, Object> getPendingPosts(int page, int size) {
        int offset = (page - 1) * size;
        List<ForumPost> list = forumPostMapper.findPendingPosts(offset, size);
        int total = forumPostMapper.countPending();
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
}