// UpdateScheduleMapper.java
package com.comic.mapper;

import com.comic.entity.UpdateSchedule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UpdateScheduleMapper {

    //@Select("SELECT s.*, c.title as comicTitle FROM update_schedule s LEFT JOIN comic c ON s.comic_id = c.id")
    List<UpdateSchedule> findAll();

    //@Select("SELECT s.*, c.title as comicTitle FROM update_schedule s LEFT JOIN comic c ON s.comic_id = c.id WHERE s.weekday = #{weekday}")
    List<UpdateSchedule> findByWeekday(Integer weekday);

    //@Insert("INSERT INTO update_schedule (comic_id, weekday) VALUES (#{comicId}, #{weekday})")
    int insert(UpdateSchedule schedule);

    //@Delete("DELETE FROM update_schedule WHERE id = #{id}")
    int deleteById(Integer id);
}