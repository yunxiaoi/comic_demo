package com.comic.controller.admin;
//用户管理

import com.comic.common.Result;
import com.comic.entity.User;
import com.comic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController extends BaseAdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result<List<User>> listUsers() {
        return Result.success(userService.getAllUsers());
    }

    @PutMapping("/ban/{id}")
    public Result<?> banUser(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        User user = userService.getById(id);
        if (user == null) return Result.error("用户不存在");
        user.setStatus(0);
        userService.updateById(user);
        return Result.success();
    }

    @PutMapping("/unban/{id}")
    public Result<?> unbanUser(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        User user = userService.getById(id);
        if (user == null) return Result.error("用户不存在");
        user.setStatus(1);
        userService.updateById(user);
        return Result.success();
    }

    @PutMapping("/reset-password/{id}")
    public Result<?> resetPassword(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        // 重置为默认密码 123456
        String encoded = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("123456");
        userService.updatePassword(id, encoded);
        return Result.success();
    }
}