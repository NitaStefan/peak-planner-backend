package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.dto.ScheduleUpdateRequest;
import com.stefan.peak_planner.model.Activity;
import com.stefan.peak_planner.model.DayOfWeek;
import com.stefan.peak_planner.model.WeekDay;
import com.stefan.peak_planner.service.DayOfWeekService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/days-of-week")
public class DayOfWeekController {

    private final DayOfWeekService dayOfWeekService;

    public DayOfWeekController(DayOfWeekService dayOfWeekService) {
        this.dayOfWeekService = dayOfWeekService;
    }

    @GetMapping("/{day}")
    public ResponseEntity<List<Activity>> getDayOfWeekActivities(@PathVariable WeekDay day) {

        DayOfWeek dayOfWeek = dayOfWeekService.getDayOfWeek(null, day);

        return new ResponseEntity<>(dayOfWeek.getActivities(), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<DayOfWeek>> getDaysOfWeek() {

        List<DayOfWeek> daysOfWeek = dayOfWeekService.getDaysOfWeek(null);

        return new ResponseEntity<>(daysOfWeek, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<DayOfWeek>> saveSchedule(@RequestBody ScheduleUpdateRequest scheduleUpdateRequest) {

        List<DayOfWeek> dbWeek = dayOfWeekService.updateSchedule(null, scheduleUpdateRequest);

        return new ResponseEntity<>(dbWeek, HttpStatus.CREATED);
    }

}
