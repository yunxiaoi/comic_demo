// ComicService.java
package com.comic.service;

import com.comic.entity.Comic;
import java.util.List;
import java.util.Map;

public interface ComicService {
    Comic getById(Integer id);
    void save(Comic comic);
    void update(Comic comic);
    void deleteById(Integer id);
    List<Comic> getHotSales(int limit);
    List<Comic> getHighViews(int limit);
    List<Comic> getFinishedTop(int limit);
    Map<String, Object> search(String keyword, Integer genreId, Integer regionId, Integer status, Integer isPaid, int page, int size);
    void incrementViews(Integer id);
    void incrementOrders(Integer id);
    void incrementFavorites(Integer id);
    void decrementFavorites(Integer id);
    List<Comic> getTopByViews(int limit);
    List<Comic> getTopByOrders(int limit);
    List<Comic> getTopByFavorites(int limit);
    Map<Integer, List<Comic>> getScheduleByWeekday();
    List<Comic> getByGenre(Integer genreId);
    List<Comic> getByRegion(Integer regionId);
    Map<String, Object> getPage(int page, int size, String keyword);
    // 分页查询所有漫画（后台用）
    Map<String, Object> listAll(int page, int size);
    // 统计漫画总数
    int count();
    Map<String, Object> listByCondition(Integer genreId, Integer regionId, Integer status, Integer isPaid, int page, int size);
}