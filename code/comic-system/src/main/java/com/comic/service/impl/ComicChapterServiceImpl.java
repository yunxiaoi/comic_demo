// ComicChapterServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.ComicChapter;
import com.comic.mapper.ComicChapterMapper;
import com.comic.service.ComicChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicChapterServiceImpl implements ComicChapterService {

    @Autowired
    private ComicChapterMapper chapterMapper;

    @Override
    public ComicChapter getById(Integer id) {
        return chapterMapper.findById(id);
    }

    @Override
    public List<ComicChapter> getByComicId(Integer comicId) {
        return chapterMapper.findByComicId(comicId);
    }

    @Override
    public void save(ComicChapter chapter) {
        chapterMapper.insert(chapter);
    }

    @Override
    public void update(ComicChapter chapter) {
        chapterMapper.update(chapter);
    }

    @Override
    public void deleteById(Integer id) {
        chapterMapper.deleteById(id);
    }
}