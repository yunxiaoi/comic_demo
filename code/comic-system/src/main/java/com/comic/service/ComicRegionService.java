// ComicRegionService.java
package com.comic.service;

import com.comic.entity.ComicRegion;
import java.util.List;

public interface ComicRegionService {
    List<ComicRegion> getAll();
    ComicRegion getById(Integer id);
    void save(ComicRegion region);
    void update(ComicRegion region);
    void deleteById(Integer id);
}