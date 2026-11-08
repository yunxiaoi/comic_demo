package com.comic.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String phone;
    private String code;
    private String username;
    private String password;
    private String nickname;
}