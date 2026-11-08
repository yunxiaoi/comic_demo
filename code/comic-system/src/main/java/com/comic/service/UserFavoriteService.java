// UserFavoriteService.java
package com.comic.service;

import com.comic.entity.UserFavorite;
import java.util.List;

public interface UserFavoriteService {
    void add(Integer userId, Integer comicId);
    void remove(Integer userId, Integer comicId);
    boolean exists(Integer userId, Integer comicId);
    List<UserFavorite> getByUserId(Integer userId);
}