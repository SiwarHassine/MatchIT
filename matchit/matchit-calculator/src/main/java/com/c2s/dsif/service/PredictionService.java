package com.c2s.dsif.service;

import com.c2s.dsif.request_response.PredictionRequest;
import org.springframework.web.client.RestTemplate;

public interface PredictionService {
	 public String makePrediction(PredictionRequest request) ;

}
