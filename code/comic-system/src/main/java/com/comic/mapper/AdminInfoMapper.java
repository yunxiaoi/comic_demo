package com.comic.mapper;

import com.comic.entity.AdminInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminInfoMapper {
    @Select("SELECT * FROM admin_info WHERE user_id = #{userId}")
    AdminInfo findByUserId(Integer userId);

    @Insert("INSERT INTO admin_info (user_id, real_name) VALUES (#{userId}, #{realName})")
    int insert(AdminInfo adminInfo);

    @Update("UPDATE admin_info SET real_name = #{realName} WHERE user_id = #{userId}")
    int update(AdminInfo adminInfo);
}