// ComicChapterService.java
package com.comic.service;

import com.comic.entity.ComicChapter;
import java.util.List;

public interface ComicChapterService {
    ComicChapter getById(Integer id);
    List<ComicChapter> getByComicId(Integer comicId);
    void save(ComicChapter chapter);
    void update(ComicChapter chapter);
    void deleteById(Integer id);
}