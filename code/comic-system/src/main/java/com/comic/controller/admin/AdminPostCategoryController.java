package com.comic.controller.admin;
//帖子分类管理

import com.comic.common.Result;
import com.comic.entity.PostCategory;
import com.comic.service.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin/post-category")
public class AdminPostCategoryController extends BaseAdminController {

    @Autowired
    private PostCategoryService categoryService;

    @GetMapping("/list")
    public Result<List<PostCategory>> list(HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(categoryService.getAll());
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody PostCategory category, HttpServletRequest request) {
        checkAdmin(request);
        categoryService.save(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody PostCategory category, HttpServletRequest request) {
        checkAdmin(request);
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        categoryService.deleteById(id);
        return Result.success();
    }
}