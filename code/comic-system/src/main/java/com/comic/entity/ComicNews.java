package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComicNews {
    private Integer id;
    private String title;
    private String content;
    private String cover;
    private Integer views;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}