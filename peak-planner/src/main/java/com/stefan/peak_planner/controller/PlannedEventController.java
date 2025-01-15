package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.model.Activity;
import com.stefan.peak_planner.model.EventDetails;
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

        PlannedEvent dbPlannedEvent = plannedEventService.addPlannedEvent(plannedEvent);

        return new ResponseEntity<>(dbPlannedEvent, HttpStatus.CREATED);
    }

    @PostMapping("/{plannedEventId}/event-details")
    public ResponseEntity<EventDetails> addEventDetailsToPlannedEvent
            (@PathVariable int plannedEventId, @RequestBody EventDetails eventDetails)
    {
        EventDetails dbEventDetails = plannedEventService.addEventDetailsToPlannedEvent(plannedEventId, eventDetails);

        return new ResponseEntity<>(dbEventDetails, HttpStatus.CREATED);
    }
}
