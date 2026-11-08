package com.comic.controller.user;
//漫画浏览、分类、排行

import com.comic.common.Result;
import com.comic.entity.Comic;
import com.comic.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/comic")
public class UserComicController {

    @Autowired
    private ComicService comicService;

    @GetMapping("/hotSales")
    public Result<List<Comic>> getHotSales() {
        return Result.success(comicService.getHotSales(10));
    }

    @GetMapping("/highViews")
    public Result<List<Comic>> getHighViews() {
        return Result.success(comicService.getHighViews(10));
    }

    @GetMapping("/finished")
    public Result<List<Comic>> getFinished() {
        return Result.success(comicService.getFinishedTop(5));
    }


    @GetMapping("/list")
    public Result<Map<String, Object>> listComics(@RequestParam(required = false) Integer genreId,
                                                  @RequestParam(required = false) Integer regionId,
                                                  @RequestParam(required = false) Integer status,
                                                  @RequestParam(required = false) Integer isPaid,
                                                  @RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "20") Integer size) {
        return Result.success(comicService.listByCondition(genreId, regionId, status, isPaid, page, size));
    }

    @GetMapping("/rank/views")
    public Result<List<Comic>> rankByViews() {
        return Result.success(comicService.getTopByViews(20));
    }
    @GetMapping("/rank/orders")
    public Result<List<Comic>> rankByOrders() {
        return Result.success(comicService.getTopByOrders(20));
    }

    @GetMapping("/rank/favorites")
    public Result<List<Comic>> rankByFavorites() {
        return Result.success(comicService.getTopByFavorites(20));
    }
    @GetMapping("/schedule")
    public Result<Map<Integer, List<Comic>>> getSchedule() {
        return Result.success(comicService.getScheduleByWeekday());
    }
    @GetMapping("/search")
    public Result<Map<String, Object>> search(@RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) Integer genreId,
                                              @RequestParam(required = false) Integer regionId,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam(required = false) Integer isPaid,
                                              @RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "20") Integer size) {
        return Result.success(comicService.search(keyword, genreId, regionId, status, isPaid, page, size));
    }

    @GetMapping("/detail/{id}")
    public Result<Comic> getDetail(@PathVariable Integer id) {
        comicService.incrementViews(id);
        return Result.success(comicService.getById(id));
    }


}