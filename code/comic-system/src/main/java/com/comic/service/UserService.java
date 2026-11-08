package com.comic.service;

import com.comic.entity.User;
import java.util.List;
import java.util.Map;

public interface UserService {
    User getById(Integer id);
    User getByUsername(String username);
    User getByPhone(String phone);
    void save(User user);
    void updateById(User user);
    void updatePassword(Integer id, String newPassword);
    boolean addBalance(Integer id, Double amount);
    boolean deductBalance(Integer id, Double amount);
    List<User> getAllUsers();
    void updateStatus(Integer id, Integer status);
    Map<String, Object> listAll(int page, int size);
}