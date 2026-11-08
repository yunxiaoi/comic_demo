package com.comic.mapper;

import com.comic.entity.ComicGenre;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComicGenreMapper {
    @Select("SELECT * FROM comic_genre ORDER BY id")
    List<ComicGenre> findAll();

    @Select("SELECT * FROM comic_genre WHERE id = #{id}")
    ComicGenre findById(Integer id);

    @Insert("INSERT INTO comic_genre (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ComicGenre genre);

    @Update("UPDATE comic_genre SET name = #{name} WHERE id = #{id}")
    int update(ComicGenre genre);

    @Delete("DELETE FROM comic_genre WHERE id = #{id}")
    int deleteById(Integer id);
}