package com.comic.controller.admin;
//公告管理

import com.comic.common.Result;
import com.comic.entity.Notice;
import com.comic.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin/notice")
public class AdminNoticeController extends BaseAdminController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Result<List<Notice>> list(HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(noticeService.getAll());
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody Notice notice, HttpServletRequest request) {
        checkAdmin(request);
        noticeService.save(notice);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Notice notice, HttpServletRequest request) {
        checkAdmin(request);
        noticeService.update(notice);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        noticeService.deleteById(id);
        return Result.success();
    }
    @PostMapping("/save")
    public Result<?> save(@RequestBody Notice notice) {
        if (notice.getId() == null) {
            noticeService.save(notice);
        } else {
            noticeService.update(notice);
        }
        return Result.success();
    }
}