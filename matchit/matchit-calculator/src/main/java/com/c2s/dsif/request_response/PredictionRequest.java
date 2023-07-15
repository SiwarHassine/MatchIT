package com.c2s.dsif.request_response;

public class PredictionRequest {

    private double score;
    private double score_experience;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore_experience() {
        return score_experience;
    }

    public void setScore_experience(double scoreExperience) {
        this.score_experience = scoreExperience;
    }
}

