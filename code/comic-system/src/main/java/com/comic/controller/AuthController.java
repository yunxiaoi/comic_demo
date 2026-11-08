package com.comic.controller;

import cn.hutool.core.util.RandomUtil;
import com.comic.common.Result;
import com.comic.dto.LoginDTO;
import com.comic.dto.RegisterDTO;
import com.comic.entity.User;
import com.comic.service.UserService;
import com.comic.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 发送验证码（模拟）
    @PostMapping("/sendCode")
    public Result<?> sendCode(@RequestParam String phone) {
        // 实际应调用短信服务，这里仅模拟，返回固定验证码123456
        return Result.success("验证码已发送，模拟验证码：123456");
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO dto) {
        // 原有校验手机号是否已存在
        if (userService.getByPhone(dto.getPhone()) != null) {
            return Result.error("手机号已注册");
        }
        // 新增：校验用户名是否已存在
        if (userService.getByUsername(dto.getUsername()) != null) {
            return Result.error("用户名已存在");
        }
        // 验证码校验（固定123456）
        if (!"123456".equals(dto.getCode())) {
            return Result.error("验证码错误");
        }
        // 创建用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setNickname(dto.getNickname());
        user.setRole(0);
        user.setStatus(1);
        user.setBalance(0.0);
        try {
            userService.save(user);
        } catch (Exception e) {
            // 捕获其他数据库异常，如唯一键冲突（理论上前面已校验，但以防万一）
            return Result.error("注册失败，用户名或手机号可能已被占用");
        }
        return Result.success();
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO dto) {
        User user = null;
        if (dto.getUsername() != null) {
            user = userService.getByUsername(dto.getUsername());
        } else if (dto.getPhone() != null) {
            user = userService.getByPhone(dto.getPhone());
        }
        if (user == null) {
            return Result.error("用户不存在");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return Result.error("密码错误");
        }
        if (user.getStatus() == 0) {
            return Result.error("账号已被封禁");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getUsername(), user.getRole());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        return Result.success(map);
    }
}