package com.example.pk_pl.controller;

import com.example.pk_pl.dto.AuthenticationResponse;
import com.example.pk_pl.model.User;
import com.example.pk_pl.service.AuthService;
import com.example.pk_pl.service.DayOfWeekService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @MockBean
    private DayOfWeekService dayOfWeekService;

    @Test
    void registerReturnsCreated() throws Exception {
        when(authService.register(any())).thenReturn(new AuthenticationResponse("a","r"));
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isCreated());
        verify(dayOfWeekService).createDaysOfWeek(any());
    }

    @Test
    void loginReturnsOk() throws Exception {
        when(authService.authenticate(any())).thenReturn(new AuthenticationResponse("a","r"));
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }
}
