package com.example.pk_pl.controller;

import com.example.pk_pl.model.PlannedEvent;
import com.example.pk_pl.service.PlannedEventService;
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

//    @GetMapping
//    public ResponseEntity<List<PlannedEvent>> getPlannedEvents() {
//
//        List<PlannedEvent> plannedEvents = plannedEventService.getPlannedEvents(null);
//
//        return new ResponseEntity<>(plannedEvents, HttpStatus.OK);
//    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<PlannedEvent>> getUpcomingPlannedEvents() {

        List<PlannedEvent> plannedEvents = plannedEventService.getUpcomingPlannedEvents(null);

        return ResponseEntity.ok(plannedEvents);
    }

    @GetMapping("/past")
    public ResponseEntity<List<PlannedEvent>> getPastPlannedEvents() {

        List<PlannedEvent> plannedEvents = plannedEventService.getPastPlannedEvents(null);

        return ResponseEntity.ok(plannedEvents);
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
