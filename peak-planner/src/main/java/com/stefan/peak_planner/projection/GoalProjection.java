package com.stefan.peak_planner.projection;

import java.time.Instant;

public interface GoalProjection {
    int getId();
    String getTitle();
    Instant getStartDate();
}