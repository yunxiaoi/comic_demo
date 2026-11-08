package com.comic.mapper;

import com.comic.entity.ComicRegion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComicRegionMapper {
    @Select("SELECT * FROM comic_region ORDER BY id")
    List<ComicRegion> findAll();

    @Select("SELECT * FROM comic_region WHERE id = #{id}")
    ComicRegion findById(Integer id);

    @Insert("INSERT INTO comic_region (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ComicRegion region);

    @Update("UPDATE comic_region SET name = #{name} WHERE id = #{id}")
    int update(ComicRegion region);

    @Delete("DELETE FROM comic_region WHERE id = #{id}")
    int deleteById(Integer id);
}