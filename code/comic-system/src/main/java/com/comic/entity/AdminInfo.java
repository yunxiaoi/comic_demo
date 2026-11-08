package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminInfo {
    private Integer id;
    private Integer userId;
    private String realName;
    private LocalDateTime createTime;
}