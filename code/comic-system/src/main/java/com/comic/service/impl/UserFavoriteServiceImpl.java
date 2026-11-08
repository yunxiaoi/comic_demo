// UserFavoriteServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.UserFavorite;
import com.comic.mapper.UserFavoriteMapper;
import com.comic.service.ComicService;
import com.comic.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavoriteServiceImpl implements UserFavoriteService {

    @Autowired
    private UserFavoriteMapper favoriteMapper;
    @Autowired
    private ComicService comicService;

    @Override
    public void add(Integer userId, Integer comicId) {
        if (exists(userId, comicId)) {
            return;
        }
        favoriteMapper.insert(userId, comicId);
        comicService.incrementFavorites(comicId);
    }

    @Override
    public void remove(Integer userId, Integer comicId) {
        favoriteMapper.delete(userId, comicId);
        comicService.decrementFavorites(comicId);
    }

    @Override
    public boolean exists(Integer userId, Integer comicId) {
        return favoriteMapper.exists(userId, comicId) != null;
    }

    @Override
    public List<UserFavorite> getByUserId(Integer userId) {
        return favoriteMapper.findByUserId(userId);
    }
}