package com.comic.controller.user;
//个人信息

import com.comic.common.Result;
import com.comic.dto.ChangePasswordDTO;
import com.comic.dto.UpdateProfileDTO;
import com.comic.entity.User;
import com.comic.service.UserService;
import com.comic.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private FileUploadUtils fileUploadUtils;

    private Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        User user = userService.getById(getUserId(request));
        user.setPassword(null);
        return Result.success(user);
    }

    @PutMapping("/info")
    public Result<?> updateUserInfo(@RequestBody UpdateProfileDTO dto, HttpServletRequest request) {
        User user = userService.getById(getUserId(request));
        if (dto.getNickname() != null) user.setNickname(dto.getNickname());
        if (dto.getAvatar() != null) user.setAvatar(dto.getAvatar());
        userService.updateById(user);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<?> changePassword(@RequestBody ChangePasswordDTO dto, HttpServletRequest request) {
        User user = userService.getById(getUserId(request));
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            return Result.error("原密码错误");
        }
        String newEncoded = passwordEncoder.encode(dto.getNewPassword());
        userService.updatePassword(user.getId(), newEncoded);
        return Result.success();
    }
    @PostMapping("/upload-avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUploadUtils.upload(file);
        return Result.success(url);
    }
}