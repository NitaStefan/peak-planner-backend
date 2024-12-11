package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.model.Activity;
import com.stefan.peak_planner.service.DayOfWeekService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayOfWeekController {

    private DayOfWeekService dayOfWeekService;

    public DayOfWeekController(DayOfWeekService dayOfWeekService) {
        this.dayOfWeekService = dayOfWeekService;
    }

    @PostMapping("/days-of-week/{dayId}/activities")
    public ResponseEntity<Activity> addActivityToDayOfWeek(@PathVariable int dayId, @RequestBody Activity activity) {

        Activity dbActivity = dayOfWeekService.addActivityToDayOfWeek(dayId, activity);

        return ResponseEntity.status(HttpStatus.CREATED).body(dbActivity);
    }

}
