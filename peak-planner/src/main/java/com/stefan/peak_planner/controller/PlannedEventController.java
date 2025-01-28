package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.model.PlannedEvent;
import com.stefan.peak_planner.service.PlannedEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planned-events")
public class PlannedEventController {

    private final PlannedEventService plannedEventService;

    public PlannedEventController(PlannedEventService plannedEventService) {
        this.plannedEventService = plannedEventService;
    }

    @GetMapping
    public ResponseEntity<List<PlannedEvent>> getPlannedEvents() {

        List<PlannedEvent> plannedEvents = plannedEventService.getPlannedEvents(null);

        return new ResponseEntity<>(plannedEvents, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlannedEvent> addPlannedEvent(@RequestBody PlannedEvent plannedEvent) {

        PlannedEvent dbPlannedEvent = plannedEventService.savePlannedEvent(plannedEvent);

        return new ResponseEntity<>(dbPlannedEvent, HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<PlannedEvent> updatePlannedEvent(@RequestBody PlannedEvent plannedEvent) {

        PlannedEvent dbPlannedEvent = plannedEventService.savePlannedEvent(plannedEvent);

        return new ResponseEntity<>(dbPlannedEvent, HttpStatus.OK);
    }

    @DeleteMapping("/{plannedEventId}")
    public ResponseEntity<Void> deletePlannedEvent(@PathVariable int plannedEventId)
    {
        plannedEventService.deletePlannedEvent(plannedEventId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
