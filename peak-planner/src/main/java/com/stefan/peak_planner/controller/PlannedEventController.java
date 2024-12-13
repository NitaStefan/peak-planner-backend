package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.model.PlannedEvent;
import com.stefan.peak_planner.service.PlannedEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlannedEventController {

    private final PlannedEventService plannedEventService;

    public PlannedEventController(PlannedEventService plannedEventService) {
        this.plannedEventService = plannedEventService;
    }

    @PostMapping("/planned-events")
    public ResponseEntity<PlannedEvent> addPlannedEvent(@RequestBody PlannedEvent plannedEvent) {

        PlannedEvent dbPlannedEvent = plannedEventService.addPlannedEvent(plannedEvent);

        return new ResponseEntity<>(dbPlannedEvent, HttpStatus.CREATED);
    }
}
