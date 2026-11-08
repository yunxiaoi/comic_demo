package com.comic.controller.user;
//阅读记录

import com.comic.common.Result;
import com.comic.entity.UserReadHistory;
import com.comic.service.UserReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/history")
public class UserReadHistoryController {

    @Autowired
    private UserReadHistoryService readHistoryService;

    private Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    @GetMapping("/list")
    public Result<List<UserReadHistory>> getReadHistory(HttpServletRequest request) {
        Integer userId = getUserId(request);
        return Result.success(readHistoryService.getByUserId(userId));
    }
}