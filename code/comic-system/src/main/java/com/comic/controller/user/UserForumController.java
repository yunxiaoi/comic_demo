package com.comic.controller.user;
//论坛帖子

import com.comic.common.Result;
import com.comic.dto.ForumPostDTO;
import com.comic.entity.ForumPost;
import com.comic.service.ForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/forum")
public class UserForumController {

    @Autowired
    private ForumPostService forumPostService;

    private Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    @GetMapping("/posts")
    public Result<Map<String, Object>> getApprovedPosts(@RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "20") Integer size) {
        return Result.success(forumPostService.getApprovedPosts(page, size));
    }

    @GetMapping("/post/{id}")
    public Result<ForumPost> getPostDetail(@PathVariable Integer id) {
        ForumPost post = forumPostService.getById(id);
        forumPostService.incrementViews(id);
        return Result.success(post);
    }

    @PostMapping("/post")
    public Result<?> createPost(@RequestBody ForumPostDTO dto, HttpServletRequest request) {
        ForumPost post = new ForumPost();
        post.setUserId(getUserId(request));
        post.setCategoryId(dto.getCategoryId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setStatus(0); // 待审核
        forumPostService.save(post);
        return Result.success("发布成功，等待审核");
    }

    @PutMapping("/post/{id}")
    public Result<?> updatePost(@PathVariable Integer id, @RequestBody ForumPostDTO dto, HttpServletRequest request) {
        ForumPost exist = forumPostService.getById(id);
        if (exist == null || !exist.getUserId().equals(getUserId(request))) {
            return Result.error("无权修改");
        }
        exist.setCategoryId(dto.getCategoryId());
        exist.setTitle(dto.getTitle());
        exist.setContent(dto.getContent());
        exist.setStatus(0); // 修改后需重新审核
        forumPostService.update(exist);
        return Result.success("修改成功，等待审核");
    }

    @DeleteMapping("/post/{id}")
    public Result<?> deletePost(@PathVariable Integer id, HttpServletRequest request) {
        ForumPost exist = forumPostService.getById(id);
        if (exist == null || !exist.getUserId().equals(getUserId(request))) {
            return Result.error("无权删除");
        }
        forumPostService.deleteById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/my-posts")
    public Result<List<ForumPost>> getMyPosts(HttpServletRequest request) {
        return Result.success(forumPostService.getByUserId(getUserId(request)));
    }
}