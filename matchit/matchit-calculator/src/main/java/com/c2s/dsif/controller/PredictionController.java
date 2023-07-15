package com.c2s.dsif.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.c2s.dsif.exception.PredictionException;
import com.c2s.dsif.request_response.PredictionRequest;
import com.c2s.dsif.request_response.PredictionResponse;
import com.c2s.dsif.service.PredictionService;

@Controller
public class PredictionController {

	@Autowired
	private PredictionService service;

	@PostMapping("/predict")
	public ResponseEntity<Map<String, Object>> predict(@RequestBody PredictionRequest request) {
	    try {
	        String prediction = service.makePrediction(request);
	        Map<String, Object> response = new HashMap<>();
	        response.put("prediction", prediction);
	        return ResponseEntity.ok(response);
	    } catch (PredictionException e) {
	        // Gérer l'exception de prédiction
	        String errorMessage = "Erreur de prédiction : " + e.getMessage();
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("error", errorMessage);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}


}
