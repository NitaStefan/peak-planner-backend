package com.example.pk_pl.controller;

import com.example.pk_pl.model.PlannedEvent;
import com.example.pk_pl.service.PlannedEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlannedEventController.class)
public class PlannedEventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlannedEventService service;

    @Test
    void upcomingReturnsOk() throws Exception {
        when(service.getUpcomingPlannedEvents(any())).thenReturn(List.of());
        mockMvc.perform(get("/planned-events/upcoming"))
                .andExpect(status().isOk());
    }

    @Test
    void addReturnsCreated() throws Exception {
        when(service.savePlannedEvent(any())).thenReturn(new PlannedEvent());
        mockMvc.perform(post("/planned-events").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isCreated());
    }
}
