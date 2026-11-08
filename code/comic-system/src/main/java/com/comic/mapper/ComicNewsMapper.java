package com.comic.mapper;

import com.comic.entity.ComicNews;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComicNewsMapper {
    @Select("SELECT * FROM comic_news WHERE id = #{id}")
    ComicNews findById(Integer id);

    @Select("SELECT * FROM comic_news ORDER BY views DESC, create_time DESC")
    List<ComicNews> findAll();

    @Select("SELECT * FROM comic_news ORDER BY views DESC LIMIT 10")
    List<ComicNews> findHotNews();

    @Insert("INSERT INTO comic_news (title, content, cover) VALUES (#{title}, #{content}, #{cover})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ComicNews news);

    @Update("UPDATE comic_news SET title = #{title}, content = #{content}, cover = #{cover} WHERE id = #{id}")
    int update(ComicNews news);

    @Delete("DELETE FROM comic_news WHERE id = #{id}")
    int deleteById(Integer id);

    @Update("UPDATE comic_news SET views = views + 1 WHERE id = #{id}")
    int incrementViews(Integer id);

    @Select("SELECT * FROM comic_news ORDER BY id DESC LIMIT #{offset}, #{size}")
    List<ComicNews> findPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM comic_news")
    int countAll();
}