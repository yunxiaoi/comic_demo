// ComicNewsServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.ComicNews;
import com.comic.mapper.ComicNewsMapper;
import com.comic.service.ComicNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicNewsServiceImpl implements ComicNewsService {

    @Autowired
    private ComicNewsMapper newsMapper;

    @Override
    public List<ComicNews> getAll() {
        return newsMapper.findAll();
    }

    @Override
    public ComicNews getById(Integer id) {
        return newsMapper.findById(id);
    }

    @Override
    public void save(ComicNews news) {
        newsMapper.insert(news);
    }

    @Override
    public void update(ComicNews news) {
        newsMapper.update(news);
    }

    @Override
    public void deleteById(Integer id) {
        newsMapper.deleteById(id);
    }

    @Override
    public void incrementViews(Integer id) {
        newsMapper.incrementViews(id);
    }

    @Override
    public List<ComicNews> getPage(int page, int size) {
        int offset = (page - 1) * size;
        return newsMapper.findPage(offset, size);
    }
}