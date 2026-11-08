package com.comic.controller.admin;
//帖子审核

import com.comic.common.Result;
import com.comic.entity.ForumPost;
import com.comic.service.ForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/forum-post")
public class AdminForumPostController extends BaseAdminController {

    @Autowired
    private ForumPostService forumPostService;

    @GetMapping("/pending")
    public Result<Map<String, Object>> getPendingPosts(@RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "20") Integer size,
                                                       HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(forumPostService.getPendingPosts(page, size));
    }

    @GetMapping("/approved")
    public Result<Map<String, Object>> getApprovedPosts(@RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "20") Integer size,
                                                        HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(forumPostService.getApprovedPosts(page, size));
    }

    @PutMapping("/approve/{id}")
    public Result<?> approvePost(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        ForumPost post = forumPostService.getById(id);
        if (post == null) return Result.error("帖子不存在");
        post.setStatus(1);
        forumPostService.update(post);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<?> rejectPost(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        ForumPost post = forumPostService.getById(id);
        if (post == null) return Result.error("帖子不存在");
        post.setStatus(2);
        forumPostService.update(post);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deletePost(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        forumPostService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<ForumPost>> list(@RequestParam(required = false) Integer status) {
        if (status != null) {
            return Result.success(forumPostService.getByStatus(status));
        }
        return Result.success(forumPostService.getAll());
    }
    @PutMapping("/audit/{id}")
    public Result<?> audit(@PathVariable Integer id, @RequestParam Integer status) {
        forumPostService.updateStatus(id, status);
        return Result.success();
    }
}