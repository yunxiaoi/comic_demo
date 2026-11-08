package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComicRegion {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
}