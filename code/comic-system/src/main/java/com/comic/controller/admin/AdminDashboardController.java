package com.comic.controller.admin;

import com.comic.common.Result;
import com.comic.service.AdminDashboardService; // 引入正确的服务
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController extends BaseAdminController {

    @Autowired
    private AdminDashboardService adminDashboardService;  // 改为注入 AdminDashboardService

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats(HttpServletRequest request) {
        checkAdmin(request);
        // 直接调用 AdminDashboardService 提供的方法
        Map<String, Object> result = adminDashboardService.getDashboardStats(); // 获取基础统计数据
        // 注意：AdminDashboardService 中已有 dailySales、comicTypeStats、postTypeStats 的单独方法
        result.put("dailySales", adminDashboardService.getDailySalesLastWeek());
        result.put("comicTypeStats", adminDashboardService.getComicTypeDistribution());
        result.put("postTypeStats", adminDashboardService.getPostTypeDistribution());
        return Result.success(result);
    }
    @GetMapping("/daily-sales")
    public Result<Map<String, Object>> getDailySales(HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(adminDashboardService.getDailySalesLastWeek());
    }

    @GetMapping("/comic-type-distribution")
    public Result<List<Map<String, Object>>> getComicTypeDistribution(HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(adminDashboardService.getComicTypeDistribution());
    }

    @GetMapping("/post-type-distribution")
    public Result<List<Map<String, Object>>> getPostTypeDistribution(HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(adminDashboardService.getPostTypeDistribution());
    }

}