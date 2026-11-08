// PostCategoryServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.PostCategory;
import com.comic.mapper.PostCategoryMapper;
import com.comic.service.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCategoryServiceImpl implements PostCategoryService {

    @Autowired
    private PostCategoryMapper categoryMapper;

    @Override
    public List<PostCategory> getAll() {
        return categoryMapper.findAll();
    }

    @Override
    public PostCategory getById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void save(PostCategory category) {
        categoryMapper.insert(category);
    }

    @Override
    public void update(PostCategory category) {
        categoryMapper.update(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}