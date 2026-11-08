package com.comic.controller.admin;
//更新计划

import com.comic.common.Result;
import com.comic.entity.UpdateSchedule;
import com.comic.service.UpdateScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin/schedule")
public class AdminScheduleController extends BaseAdminController {

    @Autowired
    private UpdateScheduleService scheduleService;

    @GetMapping("/list/{weekday}")
    public Result<List<UpdateSchedule>> listByWeekday(@PathVariable Integer weekday, HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(scheduleService.getByWeekday(weekday));
    }

    @PostMapping("/add")
    public Result<?> addSchedule(@RequestBody UpdateSchedule schedule, HttpServletRequest request) {
        checkAdmin(request);
        scheduleService.save(schedule);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteSchedule(@PathVariable Integer id, HttpServletRequest request) {
        checkAdmin(request);
        scheduleService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/list")
    public Result<List<UpdateSchedule>> list(@RequestParam(required = false) Integer weekday) {
        if (weekday != null) {
            return Result.success(scheduleService.getByWeekday(weekday));
        } else {
            return Result.success(scheduleService.getAll());
        }
    }
    @PostMapping("/save")
    public Result<?> save(@RequestBody UpdateSchedule schedule) {
        scheduleService.save(schedule);
        return Result.success();
    }
}