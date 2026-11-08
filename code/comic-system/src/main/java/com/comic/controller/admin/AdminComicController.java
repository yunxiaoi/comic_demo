package com.comic.controller.admin;
//漫画管理

import com.comic.common.Result;
import com.comic.dto.ComicDTO;
import com.comic.entity.Comic;
import com.comic.service.ComicService;
import com.comic.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/admin/comic")
public class AdminComicController extends BaseAdminController {

    @Autowired
    private ComicService comicService;
    @Autowired
    private FileUploadUtils fileUploadUtils;

    @GetMapping("/list")
    public Result<?> listComics(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer size,
                                HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(comicService.listAll(page, size));
    }

    @PostMapping("/add")
    public Result<?> addComic(@RequestBody ComicDTO dto, HttpServletRequest request) {
        checkAdmin(request);
        Comic comic = new Comic();
        comic.setTitle(dto.getTitle());
        comic.setCover(dto.getCover());
        comic.setAuthor(dto.getAuthor());
        comic.setGenreId(dto.getGenreId());
        comic.setRegionId(dto.getRegionId());
        comic.setStatus(dto.getStatus());
        comic.setIsPaid(dto.getIsPaid());
        comic.setPrice(dto.getPrice());
        comic.setDescription(dto.getDescription());
        comicService.save(comic);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> updateComic(@RequestBody ComicDTO dto, HttpServletRequest request) {
        checkAdmin(request);
        Comic comic = comicService.getById(dto.getId());
        if (comic == null) return Result.error("漫画不存在");
        comic.setTitle(dto.getTitle());
        comic.setCover(dto.getCover());
        comic.setAuthor(dto.getAuthor());
        comic.setGenreId(dto.getGenreId());
        comic.setRegionId(dto.getRegionId());
        comic.setStatus(dto.getStatus());
        comic.setIsPaid(dto.getIsPaid());
        comic.setPrice(dto.getPrice());
        comic.setDescription(dto.getDescription());
        comicService.update(comic);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteComic(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        comicService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/upload-cover")
    public Result<String> uploadCover(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        checkAdmin(request);
        String url = fileUploadUtils.upload(file);
        return Result.success(url);
    }
    @PostMapping("/save")
    public Result<?> saveComic(@RequestBody ComicDTO dto) {
        Comic comic = new Comic();
        comic.setId(dto.getId());
        comic.setTitle(dto.getTitle());
        comic.setCover(dto.getCover());
        comic.setAuthor(dto.getAuthor());
        comic.setGenreId(dto.getGenreId());
        comic.setRegionId(dto.getRegionId());
        comic.setStatus(dto.getStatus());
        comic.setIsPaid(dto.getIsPaid());
        comic.setPrice(dto.getPrice());
        comic.setDescription(dto.getDescription());

        if (comic.getId() == null) {
            comicService.save(comic);
        } else {
            comicService.update(comic);
        }
        return Result.success();
    }
}