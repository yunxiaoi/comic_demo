// UpdateScheduleServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.UpdateSchedule;
import com.comic.mapper.UpdateScheduleMapper;
import com.comic.service.UpdateScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateScheduleServiceImpl implements UpdateScheduleService {

    @Autowired
    private UpdateScheduleMapper scheduleMapper;

    @Override
    public List<UpdateSchedule> getAll() {
        return scheduleMapper.findAll();
    }

    @Override
    public List<UpdateSchedule> getByWeekday(Integer weekday) {
        return scheduleMapper.findByWeekday(weekday);
    }

    @Override
    public void save(UpdateSchedule schedule) {
        scheduleMapper.insert(schedule);
    }

    @Override
    public void deleteById(Integer id) {
        scheduleMapper.deleteById(id);
    }
}