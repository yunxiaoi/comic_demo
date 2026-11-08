package com.comic.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String phone;
    private String password;
}