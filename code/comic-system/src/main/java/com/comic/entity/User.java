package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String nickname;
    private String avatar;
    private Integer role;
    private Double balance;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}