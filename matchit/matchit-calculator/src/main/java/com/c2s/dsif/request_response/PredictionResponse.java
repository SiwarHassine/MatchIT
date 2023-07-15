package com.c2s.dsif.request_response;

public class PredictionResponse {

    private String prediction;

    public PredictionResponse(String prediction) {
        this.prediction = prediction;
    }

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public PredictionResponse() {
		super();
	}


	
    // Getters and setters
}
