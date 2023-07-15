package com.c2s.dsif.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.c2s.dsif.entities.Application;
import com.c2s.dsif.entities.TypeApplication;

import jakarta.transaction.Transactional;

@Transactional
public interface applicationRepository extends JpaRepository<Application, Long> {
	
	Application getById(Long id);
	List<Application> findByuserIdAndType(Long userId, TypeApplication type);
	Page<Application> findByUserIdAndType(Long userId, TypeApplication type, Pageable pageable);
	List<Application> findByidOfferAndType(Long idoffer, TypeApplication type);
	public Application findByidOfferAndUserId(Long idOffer, Long userId);
		
	 Application findByUserIdAndIdOffer(Long userId, Long offerId);
	 
	@Query("SELECT a FROM Application a ORDER BY MONTH(a.dateCreation)")
	List<Application> findAllApplicationByDateCreation();

}
