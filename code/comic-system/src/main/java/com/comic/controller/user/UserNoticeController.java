package com.comic.controller.user;
//公告

import com.comic.common.Result;
import com.comic.entity.Notice;
import com.comic.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class UserNoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Result<List<Notice>> getNotices() {
        return Result.success(noticeService.getAll());
    }

    @GetMapping("/{id}")
    public Result<Notice> getNoticeDetail(@PathVariable Integer id) {
        return Result.success(noticeService.getById(id));
    }
}