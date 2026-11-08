package com.comic.mapper;

import com.comic.entity.Carousel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarouselMapper {
    @Select("SELECT * FROM carousel WHERE status = 1 ORDER BY sort")
    List<Carousel> findActive();

    @Select("SELECT * FROM carousel WHERE status = 1 ORDER BY sort ASC")
    List<Carousel> findEnabled();

    @Select("SELECT * FROM carousel ORDER BY sort")
    List<Carousel> findAll();

    @Select("SELECT * FROM carousel WHERE id = #{id}")
    Carousel findById(Integer id);

    @Insert("INSERT INTO carousel (image, link, sort, status) VALUES (#{image}, #{link}, #{sort}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Carousel carousel);

    @Update("UPDATE carousel SET image = #{image}, link = #{link}, sort = #{sort}, status = #{status} WHERE id = #{id}")
    int update(Carousel carousel);

    @Delete("DELETE FROM carousel WHERE id = #{id}")
    int deleteById(Integer id);
}