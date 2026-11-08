package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComicChapter {
    private Integer id;
    private Integer comicId;
    private Integer chapterNo;
    private String title;
    private String content;
    private LocalDateTime updateTime;
}