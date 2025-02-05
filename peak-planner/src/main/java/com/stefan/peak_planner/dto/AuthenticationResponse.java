package com.stefan.peak_planner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {

    private final String accessToken;

    private final String refreshToken;

    public AuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
