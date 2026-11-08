// ComicMapper.java
package com.comic.mapper;

import com.comic.entity.Comic;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ComicMapper {

    @Select("SELECT * FROM comic WHERE id = #{id}")
    Comic findById(Integer id);

    @Insert("INSERT INTO comic (title, cover, author, genre_id, region_id, status, is_paid, price, description) " +
            "VALUES (#{title}, #{cover}, #{author}, #{genreId}, #{regionId}, #{status}, #{isPaid}, #{price}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Comic comic);

    @Update("UPDATE comic SET title = #{title}, cover = #{cover}, author = #{author}, genre_id = #{genreId}, " +
            "region_id = #{regionId}, status = #{status}, is_paid = #{isPaid}, price = #{price}, description = #{description} " +
            "WHERE id = #{id}")
    int update(Comic comic);

    @Delete("DELETE FROM comic WHERE id = #{id}")
    int deleteById(Integer id);

    // 前台接口
    @Select("SELECT * FROM comic WHERE is_paid = 1 ORDER BY orders DESC LIMIT #{limit}")
    List<Comic> findHotSales(int limit);

    @Select("SELECT * FROM comic ORDER BY views DESC LIMIT #{limit}")
    List<Comic> findHighViews(int limit);

    @Select("SELECT * FROM comic WHERE status = 1 ORDER BY views DESC LIMIT #{limit}")
    List<Comic> findFinishedTop(int limit);

    // 搜索（动态 SQL 需用 XML 或 @SelectProvider）
    List<Comic> search(@Param("keyword") String keyword,
                       @Param("genreId") Integer genreId,
                       @Param("regionId") Integer regionId,
                       @Param("status") Integer status,
                       @Param("isPaid") Integer isPaid,
                       @Param("offset") int offset,
                       @Param("size") int size);

    int countSearch(@Param("keyword") String keyword,
                    @Param("genreId") Integer genreId,
                    @Param("regionId") Integer regionId,
                    @Param("status") Integer status,
                    @Param("isPaid") Integer isPaid);

    @Update("UPDATE comic SET views = views + 1 WHERE id = #{id}")
    void incrementViews(Integer id);

    @Update("UPDATE comic SET orders = orders + 1 WHERE id = #{id}")
    void incrementOrders(Integer id);

    @Update("UPDATE comic SET favorites = favorites + 1 WHERE id = #{id}")
    void incrementFavorites(Integer id);

    @Update("UPDATE comic SET favorites = favorites - 1 WHERE id = #{id}")
    void decrementFavorites(Integer id);

    @Select("SELECT * FROM comic ORDER BY views DESC, id DESC LIMIT #{limit}")
    List<Comic> findTopByViews(int limit);

    @Select("SELECT * FROM comic ORDER BY orders DESC, id DESC LIMIT #{limit}")
    List<Comic> findTopByOrders(int limit);

    @Select("SELECT * FROM comic ORDER BY favorites DESC, id DESC LIMIT #{limit}")
    List<Comic> findTopByFavorites(int limit);

    // 根据更新计划查询
    @Select("SELECT c.* FROM comic c JOIN update_schedule s ON c.id = s.comic_id WHERE s.weekday = #{weekday}")
    List<Comic> findByScheduleWeekday(Integer weekday);

    @Select("SELECT * FROM comic WHERE genre_id = #{genreId}")
    List<Comic> findByGenre(Integer genreId);

    @Select("SELECT * FROM comic WHERE region_id = #{regionId}")
    List<Comic> findByRegion(Integer regionId);

    // 后台分页
    List<Comic> findPage(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);

    int countAll(@Param("keyword") String keyword);

    // 统计
    @Select("SELECT g.name, COUNT(c.id) as count FROM comic_genre g LEFT JOIN comic c ON g.id = c.genre_id GROUP BY g.id")
    List<Map<String, Object>> countByGenre();
}