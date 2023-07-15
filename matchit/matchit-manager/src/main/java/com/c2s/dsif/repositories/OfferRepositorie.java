package com.c2s.dsif.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.c2s.dsif.entities.Offer;

public interface OfferRepositorie extends JpaRepository<Offer, Long>{
	
	List<Offer> findOfferByStatus(Boolean status);

	Page<Offer> findBystatus(Boolean status, PageRequest of);
	
	List<Offer> findByStatusFalseAndDateExpirationBefore(Date currentDate);                          
	

}
