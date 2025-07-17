package com.example.pk_pl.controller;

import com.example.pk_pl.model.Goal;
import com.example.pk_pl.service.GoalService;
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

@WebMvcTest(GoalController.class)
public class GoalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GoalService service;

    @Test
    void getGoalsOk() throws Exception {
        when(service.getGoals(any())).thenReturn(List.of());
        mockMvc.perform(get("/goals"))
                .andExpect(status().isOk());
    }

    @Test
    void addGoalCreated() throws Exception {
        when(service.saveGoal(any())).thenReturn(new Goal());
        mockMvc.perform(post("/goals").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isCreated());
    }
}
