package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comic {
    private Integer id;
    private String title;
    private String cover;
    private String author;
    private Integer genreId;
    private Integer regionId;
    private Integer status;
    private Integer isPaid;
    private Double price;
    private Integer views;
    private Integer orders;
    private Integer favorites;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}