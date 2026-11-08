package com.comic.controller.admin;
//轮播图管理

import com.comic.common.Result;
import com.comic.entity.Carousel;
import com.comic.service.CarouselService;
import com.comic.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/carousel")
public class AdminCarouselController extends BaseAdminController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private FileUploadUtils fileUploadUtils;


    @GetMapping("/list")
    public Result<List<Carousel>> list(HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(carouselService.getAll());
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody Carousel carousel, HttpServletRequest request) {
        checkAdmin(request);
        carouselService.save(carousel);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Carousel carousel, HttpServletRequest request) {
        checkAdmin(request);
        carouselService.update(carousel);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        carouselService.deleteById(id);
        return Result.success();
    }
    @PostMapping("/upload-cover")
    public Result<String> uploadCover(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.upload(file);
        return Result.success(url);
    }
    @PostMapping("/upload")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("接收到文件：" + file.getOriginalFilename());
        String url = fileUploadUtils.upload(file);
        return Result.success(url);
    }
    @PostMapping("/save")
    public Result<?> save(@RequestBody Carousel carousel) {
        if (carousel.getId() == null) {
            carouselService.save(carousel);
        } else {
            carouselService.update(carousel);
        }
        return Result.success();
    }
}