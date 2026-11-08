package com.comic.controller;

import com.comic.common.Result;
import com.comic.entity.*;
import com.comic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ComicNewsService comicNewsService;
    @Autowired
    private ForumPostService forumPostService;
    @Autowired
    private PostCategoryService postCategoryService;
    @Autowired
    private ComicGenreService genreService;
    @Autowired
    private ComicRegionService regionService;
    @Autowired
    private ComicService comicService;
    @GetMapping("/genres")
    public Result<List<ComicGenre>> getGenres() {
        return Result.success(genreService.getAll());
    }

    @GetMapping("/regions")
    public Result<List<ComicRegion>> getRegions() {
        return Result.success(regionService.getAll());
    }

    @GetMapping("/carousels")
    public Result<List<Carousel>> getCarousels() {
        return Result.success(carouselService.getEnabledCarousels());
    }

    @GetMapping("/notices")
    public Result<List<Notice>> getNotices() {
        return Result.success(noticeService.getAll());
    }

    @GetMapping("/notice/{id}")
    public Result<Notice> getNotice(@PathVariable Integer id) {
        return Result.success(noticeService.getById(id));
    }

    @GetMapping("/news")
    public Result<List<ComicNews>> getNews(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(comicNewsService.getPage(page, size));
    }

    @GetMapping("/news/{id}")
    public Result<ComicNews> getNewsDetail(@PathVariable Integer id) {
        comicNewsService.incrementViews(id);
        return Result.success(comicNewsService.getById(id));
    }
    @GetMapping("/post-categories")
    public Result<List<PostCategory>> getPostCategories() {
        return Result.success(postCategoryService.getAll());
    }
    @GetMapping("/posts")
    public Result<Map<String, Object>> getPosts(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(forumPostService.getApprovedPosts(page, size));
    }
    @GetMapping("/hot-sales")
    public Result<List<Comic>> getHotSales() {
        return Result.success(comicService.getHotSales(10));
    }

    @GetMapping("/high-views")
    public Result<List<Comic>> getHighViews() {
        return Result.success(comicService.getHighViews(10));
    }

    @GetMapping("/finished")
    public Result<List<Comic>> getFinished() {
        return Result.success(comicService.getFinishedTop(5));
    }

    @GetMapping("/rank/views")
    public Result<List<Comic>> rankByViews() {
        return Result.success(comicService.getTopByViews(20));
    }

}