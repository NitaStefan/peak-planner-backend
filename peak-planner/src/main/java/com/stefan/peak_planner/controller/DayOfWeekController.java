package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.service.DayOfWeekService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayOfWeekController {

    private DayOfWeekService dayOfWeekService;

    public DayOfWeekController(DayOfWeekService dayOfWeekService) {
        this.dayOfWeekService = dayOfWeekService;
    }


}
