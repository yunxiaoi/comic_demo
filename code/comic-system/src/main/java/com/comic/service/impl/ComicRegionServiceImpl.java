// ComicRegionServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.ComicRegion;
import com.comic.mapper.ComicRegionMapper;
import com.comic.service.ComicRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicRegionServiceImpl implements ComicRegionService {

    @Autowired
    private ComicRegionMapper regionMapper;

    @Override
    public List<ComicRegion> getAll() {
        return regionMapper.findAll();
    }

    @Override
    public ComicRegion getById(Integer id) {
        return regionMapper.findById(id);
    }

    @Override
    public void save(ComicRegion region) {
        regionMapper.insert(region);
    }

    @Override
    public void update(ComicRegion region) {
        regionMapper.update(region);
    }

    @Override
    public void deleteById(Integer id) {
        regionMapper.deleteById(id);
    }
}