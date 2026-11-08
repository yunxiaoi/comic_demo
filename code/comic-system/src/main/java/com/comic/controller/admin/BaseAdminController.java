package com.comic.controller.admin;
//管理员权限基础类

import com.comic.common.Result;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseAdminController {

    protected Integer getAdminId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    protected void checkAdmin(HttpServletRequest request) {
        Integer role = (Integer) request.getAttribute("role");
        if (role == null || role != 1) {
            throw new RuntimeException("无权访问");
        }
    }
}