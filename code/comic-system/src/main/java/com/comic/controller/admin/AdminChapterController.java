package com.comic.controller.admin;
//章节管理

import com.comic.common.Result;
import com.comic.dto.ChapterDTO;
import com.comic.entity.ComicChapter;
import com.comic.service.ComicChapterService;
import com.comic.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/chapter")
public class AdminChapterController extends BaseAdminController {

    @Autowired
    private ComicChapterService chapterService;
    @Autowired
    private FileUploadUtils fileUploadUtils;

    @GetMapping("/list/{comicId}")
    public Result<List<ComicChapter>> listByComic(@PathVariable Integer comicId, HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(chapterService.getByComicId(comicId));
    }

    @PostMapping("/add")
    public Result<?> addChapter(@RequestBody ChapterDTO dto, HttpServletRequest request) {
        checkAdmin(request);
        ComicChapter chapter = new ComicChapter();
        chapter.setComicId(dto.getComicId());
        chapter.setChapterNo(dto.getChapterNo());
        chapter.setTitle(dto.getTitle());
        chapter.setContent(dto.getContent());
        chapterService.save(chapter);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> updateChapter(@RequestBody ChapterDTO dto, HttpServletRequest request) {
        checkAdmin(request);
        ComicChapter chapter = chapterService.getById(dto.getId());
        if (chapter == null) return Result.error("章节不存在");
        chapter.setTitle(dto.getTitle());
        chapter.setContent(dto.getContent());
        chapterService.update(chapter);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteChapter(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        chapterService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/upload-image")
    public Result<String> uploadChapterImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.upload(file);
        return Result.success(url);
    }
    @PostMapping("/save")
    public Result<?> saveChapter(@RequestBody ChapterDTO dto) {
        ComicChapter chapter = new ComicChapter();
        chapter.setId(dto.getId());
        chapter.setComicId(dto.getComicId());
        chapter.setChapterNo(dto.getChapterNo());
        chapter.setTitle(dto.getTitle());
        chapter.setContent(dto.getContent());  // JSON 字符串（图片 URL 列表）
        if (chapter.getId() == null) {
            chapterService.save(chapter);
        } else {
            chapterService.update(chapter);
        }
        return Result.success();
    }
}