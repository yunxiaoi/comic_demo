package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserFavorite {
    private Integer id;
    private Integer userId;
    private Integer comicId;
    private LocalDateTime createTime;
    private Comic comic;
}