package com.comic.dto;

import lombok.Data;

@Data
public class ChapterDTO {
    private Integer id;
    private Integer comicId;
    private Integer chapterNo;
    private String title;
    private String content; // JSON array of image urls
}