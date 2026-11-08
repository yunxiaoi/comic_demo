package com.comic.mapper;

import com.comic.entity.PostCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostCategoryMapper {
    @Select("SELECT * FROM post_category ORDER BY id")
    List<PostCategory> findAll();

    @Select("SELECT * FROM post_category WHERE id = #{id}")
    PostCategory findById(Integer id);

    @Insert("INSERT INTO post_category (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PostCategory category);

    @Update("UPDATE post_category SET name = #{name} WHERE id = #{id}")
    int update(PostCategory category);

    @Delete("DELETE FROM post_category WHERE id = #{id}")
    int deleteById(Integer id);
}