package com.comic.dto;

import lombok.Data;

@Data
public class ForumPostDTO {
    private Integer id;
    private Integer categoryId;
    private String title;
    private String content;
}