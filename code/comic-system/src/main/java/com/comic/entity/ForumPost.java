package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ForumPost {
    private Integer id;
    private Integer userId;
    private Integer categoryId;
    private String title;
    private String content;
    private Integer views;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String username;
    private String categoryName;

}