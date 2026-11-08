package com.comic.controller.user;
//阅读章节

import com.comic.common.Result;
import com.comic.entity.Comic;
import com.comic.entity.ComicChapter;
import com.comic.entity.UserReadHistory;
import com.comic.service.ComicChapterService;
import com.comic.service.ComicService;
import com.comic.service.UserOrderService;
import com.comic.service.UserReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/chapter")
public class UserChapterController {

    @Autowired
    private ComicChapterService chapterService;      // 注入章节服务
    @Autowired
    private ComicService comicService;               // 注入漫画服务
    @Autowired
    private UserOrderService orderService;           // 注入订单服务
    @Autowired
    private UserReadHistoryService readHistoryService; // 注入阅读记录服务

    private Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    @GetMapping("/list/{comicId}")
    public Result<List<ComicChapter>> getChapters(@PathVariable Integer comicId) {
        return Result.success(chapterService.getByComicId(comicId));
    }

    @GetMapping("/read/{chapterId}")
    public Result<ComicChapter> readChapter(@PathVariable Integer chapterId, HttpServletRequest request) {
        Integer userId = getUserId(request);
        ComicChapter chapter = chapterService.getById(chapterId);
        if (chapter == null) {
            return Result.error("章节不存在");
        }
        Comic comic = comicService.getById(chapter.getComicId());
        if (comic.getIsPaid() == 1) {
            // 检查是否已购买
            if (orderService.getByUserAndComic(userId, comic.getId()) == null) {
                return Result.error(403, "请先购买本漫画");
            }
        }
        // 记录阅读历史
        readHistoryService.saveOrUpdate(userId, comic.getId(), chapterId);
        return Result.success(chapter);
    }
}