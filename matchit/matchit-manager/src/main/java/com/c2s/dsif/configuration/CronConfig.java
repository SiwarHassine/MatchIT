package com.c2s.dsif.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.c2s.dsif.entities.Offer;
import com.c2s.dsif.services.OfferService;

@Configuration
@EnableScheduling
public class CronConfig {
    @Autowired
    private OfferService offerService;
    
    @Scheduled(cron = "0 1 0 * * *") // 00:01 AM every day
    public void updateExpiredOffers() {
        List<Offer> activeOffers = offerService.getActiveOffers();
        for (Offer offer : activeOffers) {
            offer.setStatus(true);
            System.out.println("titre offre ");
            // Save the updated offer if needed
            offerService.saveOffer(offer);
        }
    } }

