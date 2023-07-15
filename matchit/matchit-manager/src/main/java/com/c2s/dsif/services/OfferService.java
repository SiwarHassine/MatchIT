package com.c2s.dsif.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.c2s.dsif.entities.Offer;

public interface OfferService {
	
	public Offer saveOffer(Offer offer);
	public List<Offer> getAllOffer();
	public Offer getOfferById(Long id);
	public Offer updateOffer(Long id);
	public void deleteOffer(Long id);
	public Page<Offer> findProductsWithPagination(int offset,int pageSize);
	public Page<Offer> findProductsWithPaginationAndSorting(int offset,int pageSize,String field);
	public List<Offer> findProductsWithSorting(String field);
	public List<Offer> getOfferByStatus();
	 public List<Offer> getActiveOffers() ;
	public Page<Offer> findOfferStatusWithPagination(int offset, int pageSize, Boolean status);
}
