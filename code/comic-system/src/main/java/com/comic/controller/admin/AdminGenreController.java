package com.comic.controller.admin;
//漫画题材管理

import com.comic.common.Result;
import com.comic.entity.ComicGenre;
import com.comic.service.ComicGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin/genre")
public class AdminGenreController extends BaseAdminController {

    @Autowired
    private ComicGenreService genreService;

    @GetMapping("/list")
    public Result<List<ComicGenre>> list(HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(genreService.getAll());
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody ComicGenre genre, HttpServletRequest request) {
        checkAdmin(request);
        genreService.save(genre);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody ComicGenre genre, HttpServletRequest request) {
        checkAdmin(request);
        genreService.update(genre);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        genreService.deleteById(id);
        return Result.success();
    }
    @PostMapping("/save")
    public Result<?> save(@RequestBody ComicGenre genre) {
        if (genre.getId() == null) {
            genreService.save(genre);
        } else {
            genreService.update(genre);
        }
        return Result.success();
    }
}