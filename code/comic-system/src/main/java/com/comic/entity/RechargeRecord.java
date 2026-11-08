package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RechargeRecord {
    private Integer id;
    private Integer userId;
    private Double amount;
    private LocalDateTime createTime;
}