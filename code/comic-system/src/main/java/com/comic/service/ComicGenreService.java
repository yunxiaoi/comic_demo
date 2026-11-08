// ComicGenreService.java
package com.comic.service;

import com.comic.entity.ComicGenre;
import java.util.List;

public interface ComicGenreService {
    List<ComicGenre> getAll();
    ComicGenre getById(Integer id);
    void save(ComicGenre genre);
    void update(ComicGenre genre);
    void deleteById(Integer id);
}