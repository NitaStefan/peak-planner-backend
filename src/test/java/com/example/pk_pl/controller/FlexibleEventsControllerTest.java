package com.example.pk_pl.controller;

import com.example.pk_pl.model.FlexibleEvent;
import com.example.pk_pl.service.FlexibleEventService;
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

@WebMvcTest(FlexibleEventsController.class)
public class FlexibleEventsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FlexibleEventService service;

    @Test
    void upcomingReturnsOk() throws Exception {
        when(service.getUpcomingFlexibleEvents(any())).thenReturn(List.of());
        mockMvc.perform(get("/flexible-events/upcoming"))
                .andExpect(status().isOk());
    }

    @Test
    void addReturnsCreated() throws Exception {
        when(service.saveFlexibleEvent(any())).thenReturn(new FlexibleEvent());
        mockMvc.perform(post("/flexible-events").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isCreated());
    }
}
