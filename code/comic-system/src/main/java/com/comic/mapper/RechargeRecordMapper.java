// RechargeRecordMapper.java
package com.comic.mapper;

import com.comic.entity.RechargeRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RechargeRecordMapper {

    @Select("SELECT * FROM recharge_record WHERE user_id = #{userId} ORDER BY id DESC")
    List<RechargeRecord> findByUserId(Integer userId);

    @Insert("INSERT INTO recharge_record (user_id, amount) VALUES (#{userId}, #{amount})")
    int insert(@Param("userId") Integer userId, @Param("amount") Double amount);

    @Select("SELECT * FROM recharge_record ORDER BY id DESC LIMIT #{offset}, #{size}")
    List<RechargeRecord> findPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM recharge_record")
    int countAll();

}