package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComicGenre {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
}