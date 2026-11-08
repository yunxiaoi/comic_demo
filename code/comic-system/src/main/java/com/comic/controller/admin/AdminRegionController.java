package com.comic.controller.admin;
//漫画地区管理

import com.comic.common.Result;
import com.comic.entity.ComicRegion;
import com.comic.service.ComicRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin/region")
public class AdminRegionController extends BaseAdminController {

    @Autowired
    private ComicRegionService regionService;

    @GetMapping("/list")
    public Result<List<ComicRegion>> list(HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(regionService.getAll());
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody ComicRegion region, HttpServletRequest request) {
        checkAdmin(request);
        regionService.save(region);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody ComicRegion region, HttpServletRequest request) {
        checkAdmin(request);
        regionService.update(region);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        regionService.deleteById(id);
        return Result.success();
    }
    @PostMapping("/save")
    public Result<?> save(@RequestBody ComicRegion region) {
        if (region.getId() == null) {
            regionService.save(region);
        } else {
            regionService.update(region);
        }
        return Result.success();
    }
}