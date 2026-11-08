// UserMapper.java
package com.comic.mapper;

import com.comic.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Integer id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User findByPhone(String phone);

    @Insert("INSERT INTO user (username, password, phone, nickname, role, balance, status) VALUES (#{username}, #{password}, #{phone}, #{nickname}, #{role}, #{balance}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET nickname = #{nickname}, avatar = #{avatar}, status = #{status} WHERE id = #{id}")
    int update(User user);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    int updatePassword(@Param("id") Integer id, @Param("password") String password);

    @Update("UPDATE user SET balance = balance + #{amount} WHERE id = #{id}")
    int addBalance(@Param("id") Integer id, @Param("amount") Double amount);

    @Update("UPDATE user SET balance = balance - #{amount} WHERE id = #{id} AND balance >= #{amount}")
    int deductBalance(@Param("id") Integer id, @Param("amount") Double amount);

    @Select("SELECT * FROM user WHERE role = 0 ORDER BY id DESC")
    List<User> findAllUsers();

    @Update("UPDATE user SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
    // UserMapper.java
    @Select("SELECT * FROM user WHERE role = 0 ORDER BY id DESC LIMIT #{offset}, #{size}")
    List<User> findPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM user WHERE role = 0")
    int countAll();
}