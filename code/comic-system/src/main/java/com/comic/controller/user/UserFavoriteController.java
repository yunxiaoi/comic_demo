package com.comic.controller.user;
//收藏管理

import com.comic.common.Result;
import com.comic.entity.UserFavorite;
import com.comic.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/favorite")
public class UserFavoriteController {

    @Autowired
    private UserFavoriteService favoriteService;

    private Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    @PostMapping("/add")
    public Result<?> addFavorite(@RequestParam Integer comicId, HttpServletRequest request) {
        favoriteService.add(getUserId(request), comicId);
        return Result.success();
    }

    @DeleteMapping("/remove")
    public Result<?> removeFavorite(@RequestParam Integer comicId, HttpServletRequest request) {
        favoriteService.remove(getUserId(request), comicId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<UserFavorite>> getFavorites(HttpServletRequest request) {
        return Result.success(favoriteService.getByUserId(getUserId(request)));
    }

    @GetMapping("/check")
    public Result<Boolean> checkFavorite(@RequestParam Integer comicId, HttpServletRequest request) {
        Integer userId = getUserId(request);
        return Result.success(favoriteService.exists(userId, comicId));
    }
}