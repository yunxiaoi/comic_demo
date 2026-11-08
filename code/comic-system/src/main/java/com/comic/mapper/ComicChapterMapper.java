// ComicChapterMapper.java
package com.comic.mapper;

import com.comic.entity.ComicChapter;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComicChapterMapper {

    @Select("SELECT * FROM comic_chapter WHERE id = #{id}")
    ComicChapter findById(Integer id);

    @Select("SELECT * FROM comic_chapter WHERE comic_id = #{comicId} ORDER BY chapter_no ASC")
    List<ComicChapter> findByComicId(Integer comicId);

    @Insert("INSERT INTO comic_chapter (comic_id, chapter_no, title, content) VALUES (#{comicId}, #{chapterNo}, #{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ComicChapter chapter);

    @Update("UPDATE comic_chapter SET title = #{title}, content = #{content} WHERE id = #{id}")
    int update(ComicChapter chapter);

    @Delete("DELETE FROM comic_chapter WHERE id = #{id}")
    int deleteById(Integer id);
}