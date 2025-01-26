package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.model.FlexibleEvent;
import com.stefan.peak_planner.service.FlexibleEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flexible-events")
public class FlexibleEventsController {

    private final FlexibleEventService flexibleEventService;

    public FlexibleEventsController(FlexibleEventService flexibleEventService) {
        this.flexibleEventService = flexibleEventService;
    }

    @GetMapping
    public ResponseEntity<List<FlexibleEvent>> getFlexibleEvents() {

        List<FlexibleEvent> flexibleEvents = flexibleEventService.getFlexibleEvents(null);

        return new ResponseEntity<>(flexibleEvents, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FlexibleEvent> addPlannedEvent(@RequestBody FlexibleEvent flexibleEvent) {

        FlexibleEvent dbFlexibleEvent = flexibleEventService.saveFlexibleEvent(flexibleEvent);

        return new ResponseEntity<>(dbFlexibleEvent, HttpStatus.CREATED);
    }
}
