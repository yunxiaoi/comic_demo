// ComicGenreServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.ComicGenre;
import com.comic.mapper.ComicGenreMapper;
import com.comic.service.ComicGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicGenreServiceImpl implements ComicGenreService {

    @Autowired
    private ComicGenreMapper genreMapper;

    @Override
    public List<ComicGenre> getAll() {
        return genreMapper.findAll();
    }

    @Override
    public ComicGenre getById(Integer id) {
        return genreMapper.findById(id);
    }

    @Override
    public void save(ComicGenre genre) {
        genreMapper.insert(genre);
    }

    @Override
    public void update(ComicGenre genre) {
        genreMapper.update(genre);
    }

    @Override
    public void deleteById(Integer id) {
        genreMapper.deleteById(id);
    }
}