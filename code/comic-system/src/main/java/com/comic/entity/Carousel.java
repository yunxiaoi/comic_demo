package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Carousel {
    private Integer id;
    private String image;
    private String link;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
}