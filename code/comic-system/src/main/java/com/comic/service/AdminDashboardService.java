// AdminDashboardService.java
package com.comic.service;

import java.util.List;
import java.util.Map;

public interface AdminDashboardService {
    Map<String, Object> getDashboardStats();
    Map<String, Object> getDailySalesLastWeek();
    List<Map<String, Object>> getComicTypeDistribution();
    List<Map<String, Object>> getPostTypeDistribution();
}