// ComicServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.Comic;
import com.comic.mapper.ComicMapper;
import com.comic.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComicServiceImpl implements ComicService {

    @Autowired
    private ComicMapper comicMapper;

    @Override
    public Comic getById(Integer id) {
        return comicMapper.findById(id);
    }

    @Override
    public void save(Comic comic) {
        comicMapper.insert(comic);
    }

    @Override
    public void update(Comic comic) {
        comicMapper.update(comic);
    }

    @Override
    public void deleteById(Integer id) {
        comicMapper.deleteById(id);
    }

    @Override
    public List<Comic> getHotSales(int limit) {
        return comicMapper.findHotSales(limit);
    }

    @Override
    public List<Comic> getHighViews(int limit) {
        return comicMapper.findHighViews(limit);
    }

    @Override
    public List<Comic> getFinishedTop(int limit) {
        return comicMapper.findFinishedTop(limit);
    }

    @Override
    public Map<String, Object> search(String keyword, Integer genreId, Integer regionId, Integer status, Integer isPaid, int page, int size) {
        int offset = (page - 1) * size;
        List<Comic> list = comicMapper.search(keyword, genreId, regionId, status, isPaid, offset, size);
        int total = comicMapper.countSearch(keyword, genreId, regionId, status, isPaid);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    @Override
    public void incrementViews(Integer id) {
        comicMapper.incrementViews(id);
    }

    @Override
    public void incrementOrders(Integer id) {
        comicMapper.incrementOrders(id);
    }

    @Override
    public void incrementFavorites(Integer id) {
        comicMapper.incrementFavorites(id);
    }

    @Override
    public void decrementFavorites(Integer id) {
        comicMapper.decrementFavorites(id);
    }

    @Override
    public List<Comic> getTopByViews(int limit) {
        return comicMapper.findTopByViews(limit);
    }

    @Override
    public List<Comic> getTopByOrders(int limit) {
        return comicMapper.findTopByOrders(limit);
    }

    @Override
    public List<Comic> getTopByFavorites(int limit) {
        return comicMapper.findTopByFavorites(limit);
    }

    @Override
    public Map<Integer, List<Comic>> getScheduleByWeekday() {
        Map<Integer, List<Comic>> map = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            map.put(i, comicMapper.findByScheduleWeekday(i));
        }
        return map;
    }

    @Override
    public List<Comic> getByGenre(Integer genreId) {
        return comicMapper.findByGenre(genreId);
    }

    @Override
    public List<Comic> getByRegion(Integer regionId) {
        return comicMapper.findByRegion(regionId);
    }

    @Override
    public Map<String, Object> getPage(int page, int size, String keyword) {
        int offset = (page - 1) * size;
        List<Comic> list = comicMapper.findPage(keyword, offset, size);
        int total = comicMapper.countAll(keyword);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
    @Override
    public Map<String, Object> listAll(int page, int size) {
        int offset = (page - 1) * size;
        List<Comic> list = comicMapper.findPage(null, offset, size);
        int total = comicMapper.countAll(null);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    @Override
    public int count() {
        return comicMapper.countAll(null);
    }
    @Override
    public Map<String, Object> listByCondition(Integer genreId, Integer regionId, Integer status, Integer isPaid, int page, int size) {
        // 复用已有的 search 方法，其中 keyword 传 null，其他条件直接传递
        return search(null, genreId, regionId, status, isPaid, page, size);
    }
}