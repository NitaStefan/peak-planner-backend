package com.example.pk_pl.controller;

import com.example.pk_pl.dto.ScheduleUpdateRequest;
import com.example.pk_pl.model.Activity;
import com.example.pk_pl.model.DayOfWeek;
import com.example.pk_pl.model.WeekDay;
import com.example.pk_pl.service.DayOfWeekService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DayOfWeekController.class)
public class DayOfWeekControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DayOfWeekService service;

    @Test
    void getDaysReturnsOk() throws Exception {
        when(service.getDaysOfWeek(any())).thenReturn(List.of(new DayOfWeek()));
        mockMvc.perform(get("/days-of-week"))
                .andExpect(status().isOk());
    }

    @Test
    void updateScheduleReturnsCreated() throws Exception {
        when(service.updateSchedule(any(), any(ScheduleUpdateRequest.class))).thenReturn(List.of(new DayOfWeek()));
        mockMvc.perform(put("/days-of-week").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isCreated());
    }
}
