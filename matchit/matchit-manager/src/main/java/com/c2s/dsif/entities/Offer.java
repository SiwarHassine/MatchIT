package com.c2s.dsif.entities;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name="offer")
public class Offer implements Serializable{
	
	public Offer() {
		super();
	}

	public Offer(Long id, String title, String description, Boolean status, Date dateExpiration, Date dateCreation) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.dateExpiration = dateExpiration;
		this.dateCreation = dateCreation;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	 @Column(name = "title")
	    private String title;


	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "description")
	   private String description;
	
	 @Column(name= "status")
	 private Boolean status= false;
	 
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateExpiration;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateCreation;
	
	

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	

}
