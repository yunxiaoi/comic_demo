// UpdateScheduleService.java
package com.comic.service;

import com.comic.entity.UpdateSchedule;
import java.util.List;

public interface UpdateScheduleService {
    List<UpdateSchedule> getAll();
    List<UpdateSchedule> getByWeekday(Integer weekday);
    void save(UpdateSchedule schedule);
    void deleteById(Integer id);
}