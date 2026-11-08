package com.comic.controller.admin;
//管理员个人信息修改
import com.comic.common.Result;
import com.comic.dto.ChangePasswordDTO;
import com.comic.dto.UpdateProfileDTO;
import com.comic.entity.User;
import com.comic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin/profile")
public class AdminProfileController extends BaseAdminController {

    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/info")
    public Result<User> getAdminInfo(HttpServletRequest request) {
        checkAdmin(request);
        User user = userService.getById(getAdminId(request));
        user.setPassword(null);
        return Result.success(user);
    }

    @PutMapping("/info")
    public Result<?> updateAdminInfo(@RequestBody UpdateProfileDTO dto, HttpServletRequest request) {
        checkAdmin(request);
        User user = userService.getById(getAdminId(request));
        if (dto.getNickname() != null) user.setNickname(dto.getNickname());
        if (dto.getAvatar() != null) user.setAvatar(dto.getAvatar());
        userService.updateById(user);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<?> changePassword(@RequestBody ChangePasswordDTO dto, HttpServletRequest request) {
        checkAdmin(request);
        User user = userService.getById(getAdminId(request));
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            return Result.error("原密码错误");
        }
        String newEncoded = passwordEncoder.encode(dto.getNewPassword());
        userService.updatePassword(user.getId(), newEncoded);
        return Result.success();
    }
}