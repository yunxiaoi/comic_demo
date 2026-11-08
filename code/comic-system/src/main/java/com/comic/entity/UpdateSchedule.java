package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateSchedule {
    private Integer id;
    private Integer comicId;
    private Integer weekday;
    private LocalDateTime createTime;
    private String comicTitle;
    //private Comic comic;  // 关联漫画对象
}