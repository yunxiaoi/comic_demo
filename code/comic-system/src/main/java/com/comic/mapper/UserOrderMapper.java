// UserOrderMapper.java
package com.comic.mapper;

import com.comic.entity.UserOrder;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserOrderMapper {

    @Select("SELECT * FROM user_order WHERE user_id = #{userId} AND comic_id = #{comicId}")
    UserOrder findByUserAndComic(@Param("userId") Integer userId, @Param("comicId") Integer comicId);

    @Insert("INSERT INTO user_order (user_id, comic_id, amount) VALUES (#{userId}, #{comicId}, #{amount})")
    int insert(@Param("userId") Integer userId, @Param("comicId") Integer comicId, @Param("amount") Double amount);

    //@Select("SELECT * FROM user_order WHERE user_id = #{userId} ORDER BY id DESC")
    List<UserOrder> findByUserId(Integer userId);

    // 后台分页
    List<UserOrder> findPage(@Param("username") String username, @Param("offset") int offset, @Param("size") int size);

    int countAll(@Param("username") String username);

    @Select("SELECT COALESCE(SUM(amount), 0) FROM user_order WHERE DATE(create_time) = #{date}")
    Double sumAmountByDate(LocalDate date);

}