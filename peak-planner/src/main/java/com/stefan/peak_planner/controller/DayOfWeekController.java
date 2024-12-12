package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.model.Activity;
import com.stefan.peak_planner.model.DayOfWeek;
import com.stefan.peak_planner.service.DayOfWeekService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DayOfWeekController {

    private DayOfWeekService dayOfWeekService;

    public DayOfWeekController(DayOfWeekService dayOfWeekService) {
        this.dayOfWeekService = dayOfWeekService;
    }

    @GetMapping("/days-of-week/{dayId}")
    public ResponseEntity<DayOfWeek> getDayOfWeek(@PathVariable int dayId) {

        DayOfWeek dayOfWeek = dayOfWeekService.findDayOfWeekById(dayId);

        return new ResponseEntity<>(dayOfWeek, HttpStatus.OK);
    }

    @PostMapping("/days-of-week/{dayId}/activities")
    public ResponseEntity<Activity> addActivityToDayOfWeek(@PathVariable int dayId, @RequestBody Activity activity) {

        Activity dbActivity = dayOfWeekService.addActivityToDayOfWeek(dayId, activity);

        return new ResponseEntity<>(dbActivity, HttpStatus.CREATED);
    }

}
