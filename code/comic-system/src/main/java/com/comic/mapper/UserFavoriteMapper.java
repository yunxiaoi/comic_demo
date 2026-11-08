// UserFavoriteMapper.java
package com.comic.mapper;

import com.comic.entity.UserFavorite;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserFavoriteMapper {

    @Insert("INSERT INTO user_favorite (user_id, comic_id) VALUES (#{userId}, #{comicId})")
    int insert(@Param("userId") Integer userId, @Param("comicId") Integer comicId);

    @Delete("DELETE FROM user_favorite WHERE user_id = #{userId} AND comic_id = #{comicId}")
    int delete(@Param("userId") Integer userId, @Param("comicId") Integer comicId);

    @Select("SELECT * FROM user_favorite WHERE user_id = #{userId} AND comic_id = #{comicId}")
    UserFavorite exists(@Param("userId") Integer userId, @Param("comicId") Integer comicId);

    //  @Select("SELECT f.*, c.title as comicTitle, c.cover as comicCover FROM user_favorite f LEFT JOIN comic c ON f.comic_id = c.id WHERE f.user_id = #{userId} ORDER BY f.id DESC")
    List<UserFavorite> findByUserId(Integer userId);
}