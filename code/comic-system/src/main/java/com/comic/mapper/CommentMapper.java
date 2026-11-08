// CommentMapper.java
package com.comic.mapper;

import com.comic.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("SELECT * FROM comment WHERE id = #{id}")
    Comment findById(Integer id);

    @Select("SELECT * FROM comment WHERE target_type = #{targetType} AND target_id = #{targetId} ORDER BY id DESC")
    List<Comment> findByTarget(@Param("targetType") Integer targetType, @Param("targetId") Integer targetId);

    @Select("SELECT * FROM comment WHERE user_id = #{userId} ORDER BY id DESC")
    List<Comment> findByUserId(Integer userId);

    @Select("SELECT * FROM comment ORDER BY id DESC")
    List<Comment> findAll();

    @Insert("INSERT INTO comment (user_id, target_type, target_id, content) VALUES (#{userId}, #{targetType}, #{targetId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Comment comment);

    @Delete("DELETE FROM comment WHERE id = #{id}")
    int deleteById(Integer id);

    //@Select("SELECT * FROM comment ORDER BY id DESC LIMIT #{offset}, #{size}")
    List<Comment> findPage(@Param("offset") int offset, @Param("size") int size);

    //@Select("SELECT COUNT(*) FROM comment")
    int countAll();
}