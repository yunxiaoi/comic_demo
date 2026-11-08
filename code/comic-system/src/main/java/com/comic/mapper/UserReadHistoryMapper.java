// UserReadHistoryMapper.java
package com.comic.mapper;

import com.comic.entity.UserReadHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserReadHistoryMapper {

    @Select("SELECT * FROM user_read_history WHERE user_id = #{userId} AND comic_id = #{comicId}")
    UserReadHistory findByUserAndComic(@Param("userId") Integer userId, @Param("comicId") Integer comicId);

    @Insert("INSERT INTO user_read_history (user_id, comic_id, chapter_id) VALUES (#{userId}, #{comicId}, #{chapterId})")
    int insert(@Param("userId") Integer userId, @Param("comicId") Integer comicId, @Param("chapterId") Integer chapterId);

    @Update("UPDATE user_read_history SET chapter_id = #{chapterId}, read_time = NOW() WHERE user_id = #{userId} AND comic_id = #{comicId}")
    int updateReadTime(@Param("userId") Integer userId, @Param("comicId") Integer comicId, @Param("chapterId") Integer chapterId);

    /*@Select("SELECT h.*, c.title as comicTitle, ch.title as chapterTitle FROM user_read_history h " +
            "LEFT JOIN comic c ON h.comic_id = c.id " +
            "LEFT JOIN comic_chapter ch ON h.chapter_id = ch.id " +
            "WHERE h.user_id = #{userId} ORDER BY h.read_time DESC")*/
    List<UserReadHistory> findByUserId(Integer userId);

    @Delete("DELETE FROM user_read_history WHERE user_id = #{userId}")
    int deleteByUserId(Integer userId);
}