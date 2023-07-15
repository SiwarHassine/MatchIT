package com.c2s.dsif.services;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.c2s.dsif.entities.Offer;
import com.c2s.dsif.repositories.OfferRepositorie;

@Service
public class OfferServiceImpl implements OfferService{
	
	@Autowired
	private OfferRepositorie repository;
	
	public List<Offer> getAllOffer(){
		return (List<Offer>) repository.findAll();	}

	@Override
	public Offer saveOffer(Offer offer) {
		// TODO Auto-generated method stub
		offer.setDateCreation(new Date());
		return repository.save(offer);
	}

	@Override
	public Offer getOfferById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).get();
	}
	@Override
	public List<Offer> getOfferByStatus() {
		// TODO Auto-generated method stub
		return repository.findOfferByStatus(false);
	}

	@Override
    public List<Offer> getActiveOffers() {
        Date currentDate = new Date();
        return repository.findByStatusFalseAndDateExpirationBefore(currentDate);
    }               

	@Override
	public Offer updateOffer(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOffer(Long id) {
	
		repository.deleteById(id);
		
	}
	
	
	 public List<Offer> findProductsWithSorting(String field){
	        return  repository.findAll(Sort.by(Sort.Direction.ASC,field));
	    }
	
	  public Page<Offer> findProductsWithPagination(int offset,int pageSize){
	        Page<Offer> offers = repository.findAll(PageRequest.of(offset, pageSize));
	        return  offers;
	    }
	  public Page<Offer> findOfferStatusWithPagination(int offset, int pageSize, Boolean status) {
		    Page<Offer> offers = repository.findBystatus(status, PageRequest.of(offset, pageSize));
		    return offers;
		}
	  
	  
	    public Page<Offer> findProductsWithPaginationAndSorting(int offset,int pageSize,String field){
	        Page<Offer> offers = repository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
	        return  offers;
	    }
	

}
