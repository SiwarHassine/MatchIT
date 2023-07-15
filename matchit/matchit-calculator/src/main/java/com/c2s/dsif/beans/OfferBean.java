package com.c2s.dsif.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
public class OfferBean {
	
	
	private Long id;
	private String title;
	private String description;
	private Boolean status;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateExpiration;
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public OfferBean() {
		super();
	}

	public OfferBean(Long id, String title, String description, Boolean status, Date dateExpiration) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.dateExpiration = dateExpiration;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
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
	

}
