// AdminDashboardServiceImpl.java
package com.comic.service.impl;

import com.comic.mapper.*;
import com.comic.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private ComicMapper comicMapper;
    @Autowired
    private ForumPostMapper forumPostMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserOrderMapper userOrderMapper;

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("comicCount", comicMapper.countAll(null));
        stats.put("postCount", forumPostMapper.countAll());
        stats.put("commentCount", commentMapper.countAll());
        stats.put("orderCount", userOrderMapper.countAll(null));
        return stats;
    }

    @Override
    public Map<String, Object> getDailySalesLastWeek() {
        List<String> days = new ArrayList<>();
        List<Double> amounts = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            days.add(date.format(formatter));
            Double total = userOrderMapper.sumAmountByDate(date);
            amounts.add(total == null ? 0.0 : total);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("days", days);
        result.put("amounts", amounts);
        return result;
    }

    @Override
    public List<Map<String, Object>> getComicTypeDistribution() {
        return comicMapper.countByGenre();
    }

    @Override
    public List<Map<String, Object>> getPostTypeDistribution() {
        return forumPostMapper.countByCategory();
    }
}