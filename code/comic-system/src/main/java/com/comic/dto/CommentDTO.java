package com.comic.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer targetType; // 1漫画 2帖子
    private Integer targetId;
    private String content;
    private String username;
    private String targetTitle;
}