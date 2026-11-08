package com.comic.controller.admin;
//资讯管理

import com.comic.common.Result;
import com.comic.entity.ComicNews;
import com.comic.service.ComicNewsService;
import com.comic.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/news")
public class AdminNewsController extends BaseAdminController {

    @Autowired
    private ComicNewsService newsService;
    @Autowired
    private FileUploadUtils fileUploadUtils;

    @GetMapping("/list")
    public Result<List<ComicNews>> list(HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(newsService.getAll());
    }

    @PostMapping("/add")
    public Result<?> addNews(@RequestBody ComicNews news, HttpServletRequest request) {
        checkAdmin(request);
        newsService.save(news);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> updateNews(@RequestBody ComicNews news, HttpServletRequest request) {
        checkAdmin(request);
        newsService.update(news);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteNews(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        newsService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/upload-cover")
    public Result<String> uploadCover(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        checkAdmin(request);
        String url = fileUploadUtils.upload(file);
        return Result.success(url);
    }
    @PostMapping("/save")
    public Result<?> save(@RequestBody ComicNews news) {
        if (news.getId() == null) {
            newsService.save(news);
        } else {
            newsService.update(news);
        }
        return Result.success();
    }
}