package com.comic.controller.admin;
//评论管理

import com.comic.common.Result;
import com.comic.entity.Comment;
import com.comic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/comment")
public class AdminCommentController extends BaseAdminController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    public Result<Map<String, Object>> listComments(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "20") Integer size,
                                                    HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(commentService.listAll(page, size));
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteComment(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        commentService.deleteById(id);
        return Result.success();
    }
}