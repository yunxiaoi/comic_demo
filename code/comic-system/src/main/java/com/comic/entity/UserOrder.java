package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserOrder {
    private Integer id;
    private Integer userId;
    private Integer comicId;
    private Double amount;
    private LocalDateTime createTime;
    private String comicTitle;
}