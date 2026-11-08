package com.comic.controller.user;
//评论

import com.comic.common.Result;
import com.comic.dto.CommentDTO;
import com.comic.entity.Comment;
import com.comic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/comment")
public class UserCommentController {

    @Autowired
    private CommentService commentService;

    private Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    @PostMapping("/add")
    public Result<?> addComment(@RequestBody CommentDTO dto, HttpServletRequest request) {
        Comment comment = new Comment();
        comment.setUserId(getUserId(request));
        comment.setTargetType(dto.getTargetType());
        comment.setTargetId(dto.getTargetId());
        comment.setContent(dto.getContent());
        commentService.save(comment);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Comment>> getComments(@RequestParam Integer targetType, @RequestParam Integer targetId) {
        return Result.success(commentService.getByTarget(targetType, targetId));
    }

    @GetMapping("/my-comments")
    public Result<List<Comment>> getMyComments(HttpServletRequest request) {
        return Result.success(commentService.getByUserId(getUserId(request)));
    }
}