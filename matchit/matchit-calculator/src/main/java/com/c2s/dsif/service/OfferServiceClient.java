package com.c2s.dsif.service;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.c2s.dsif.beans.OfferBean;
@FeignClient(name = "manager")
public interface OfferServiceClient {
	

    @GetMapping(value = "/offerList")
    public List<OfferBean> getOffers();
    
    @GetMapping("/getOfferNotEpired")
	   public List<OfferBean> getOffersNotEx();
    
    @GetMapping("/getOfferById/{id}")
    public OfferBean getOfferById(@PathVariable("id") Long id);
}
