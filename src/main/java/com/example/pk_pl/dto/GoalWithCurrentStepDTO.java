package com.example.pk_pl.dto;

import com.example.pk_pl.model.Goal;
import com.example.pk_pl.model.Step;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GoalWithCurrentStepDTO {

    private final int id;
    private final String title;
    private final String currStepTitle;

    private final String currStepDescription;

    private final byte currStepImpact;

    public GoalWithCurrentStepDTO(Goal goal) {
        this.id = goal.getId();
        this.title = goal.getTitle();

        Step currentStep = goal.getCurrentStep();

        this.currStepTitle = (currentStep != null) ? currentStep.getTitle() : "No Active Step";
        this.currStepDescription = (currentStep != null) ? currentStep.getDescription() : null;
        this.currStepImpact = (currentStep != null) ? currentStep.getImpact() : 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCurrStepTitle() {
        return currStepTitle;
    }

    public String getCurrStepDescription() {
        return currStepDescription;
    }

    public byte getCurrStepImpact() {
        return currStepImpact;
    }
}
