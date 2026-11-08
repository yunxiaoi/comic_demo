package com.comic.service.impl;

import com.comic.entity.UserOrder;
import com.comic.mapper.UserOrderMapper;
import com.comic.service.ComicService;
import com.comic.service.UserOrderService;
import com.comic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private UserOrderMapper orderMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ComicService comicService;

    @Override
    public UserOrder getByUserAndComic(Integer userId, Integer comicId) {
        return orderMapper.findByUserAndComic(userId, comicId);
    }

    @Override
    @Transactional
    public boolean purchase(Integer userId, Integer comicId, Double amount) {
        boolean deducted = userService.deductBalance(userId, amount);
        if (!deducted) {
            return false;
        }
        orderMapper.insert(userId, comicId, amount);
        comicService.incrementOrders(comicId);
        return true;
    }

    @Override
    public List<UserOrder> getByUserId(Integer userId) {
        return orderMapper.findByUserId(userId);
    }

    @Override
    public Map<String, Object> getPage(int page, int size, String username) {
        int offset = (page - 1) * size;
        List<UserOrder> list = orderMapper.findPage(username, offset, size);
        int total = orderMapper.countAll(username);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
    @Override
    public int count() {
        return orderMapper.countAll(null);
    }
    @Override
    public Map<String, Object> listAll(int page, int size) {
        int offset = (page - 1) * size;
        List<UserOrder> list = orderMapper.findPage(null, offset, size); // 注意：findPage 已存在，参数为 username, offset, size
        int total = orderMapper.countAll(null);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
}