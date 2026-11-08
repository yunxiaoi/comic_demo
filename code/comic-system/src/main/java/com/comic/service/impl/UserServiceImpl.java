package com.comic.service.impl;

import com.comic.entity.User;
import com.comic.mapper.UserMapper;
import com.comic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateById(User user) {
        userMapper.update(user);
    }

    @Override
    public void updatePassword(Integer id, String newPassword) {
        userMapper.updatePassword(id, newPassword);
    }

    @Override
    public boolean addBalance(Integer id, Double amount) {
        return userMapper.addBalance(id, amount) > 0;
    }

    @Override
    public boolean deductBalance(Integer id, Double amount) {
        return userMapper.deductBalance(id, amount) > 0;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        userMapper.updateStatus(id, status);
    }
    // UserServiceImpl.java
    @Override
    public Map<String, Object> listAll(int page, int size) {
        int offset = (page - 1) * size;
        List<User> list = userMapper.findPage(offset, size);
        int total = userMapper.countAll();
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
}