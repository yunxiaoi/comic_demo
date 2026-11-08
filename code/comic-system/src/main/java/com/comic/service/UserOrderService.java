// UserOrderService.java
package com.comic.service;

import com.comic.entity.UserOrder;
import java.util.List;
import java.util.Map;

public interface UserOrderService {
    UserOrder getByUserAndComic(Integer userId, Integer comicId);
    boolean purchase(Integer userId, Integer comicId, Double amount);
    List<UserOrder> getByUserId(Integer userId);
    Map<String, Object> getPage(int page, int size, String username);
    // 统计订单总数
    int count();
    // 分页查询所有订单（后台用）
    Map<String, Object> listAll(int page, int size);
}