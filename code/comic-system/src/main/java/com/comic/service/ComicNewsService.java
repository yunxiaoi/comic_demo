// ComicNewsService.java
package com.comic.service;

import com.comic.entity.ComicNews;
import java.util.List;

public interface ComicNewsService {
    List<ComicNews> getAll();
    ComicNews getById(Integer id);
    void save(ComicNews news);
    void update(ComicNews news);
    void deleteById(Integer id);
    void incrementViews(Integer id);
    List<ComicNews> getPage(int page, int size);
}