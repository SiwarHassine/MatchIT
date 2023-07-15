package com.c2s.dsif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.c2s.dsif.configuration.PredictionServiceProperties;
import com.c2s.dsif.exception.PredictionException;
import com.c2s.dsif.request_response.PredictionRequest;
import com.c2s.dsif.request_response.PredictionResponse;
@Service
public class PredictionServiceImpl implements PredictionService {

    private final RestTemplate restTemplate;
    private final PredictionServiceProperties predictionServiceProperties;

    @Autowired
    public PredictionServiceImpl(RestTemplate restTemplate, PredictionServiceProperties predictionServiceProperties) {
        this.restTemplate = restTemplate;
        this.predictionServiceProperties = predictionServiceProperties;
    }
    
    @Override
    public String makePrediction(PredictionRequest request) {
        // Appeler l'API Flask de prédiction
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("URLLL"+predictionServiceProperties.getUrl());
        HttpEntity<PredictionRequest> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<PredictionResponse> responseEntity = restTemplate.exchange(
                predictionServiceProperties.getUrl(), HttpMethod.POST, httpEntity, PredictionResponse.class);

        // Extraire la prédiction de la réponse
        PredictionResponse response = responseEntity.getBody();
        System.out.println("reponse "+ responseEntity.getBody().getPrediction());
        if (response != null) {
            return response.getPrediction();
        } else {
            throw new PredictionException("Failed to get prediction from the prediction service.");
        }
    }

}


