package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserReadHistory {
    private Integer id;
    private Integer userId;
    private Integer comicId;
    private Integer chapterId;
    private LocalDateTime readTime;
    // 临时字段，用于前端展示，不映射数据库
    private String comicTitle;
    private String chapterTitle;
}