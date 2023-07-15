package com.c2s.dsif.beans;

import com.c2s.dsif.entities.EtatApplication;
import com.c2s.dsif.entities.TypeApplication;

public class ApplicationBean {
	
	private Long id;
	private TypeApplication type;
	private EtatApplication etat;
	private Double score;
	private FileBean file;
	private OfferBean offer;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TypeApplication getType() {
		return type;
	}
	public void setType(TypeApplication type) {
		this.type = type;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public FileBean getFile() {
		return file;
	}
	public void setFile(FileBean file) {
		this.file = file;
	}
	public OfferBean getOffer() {
		return offer;
	}
	public void setOffer(OfferBean offer) {
		this.offer = offer;
	}
	
	public EtatApplication getEtat() {
		return etat;
	}
	public void setEtat(EtatApplication etat) {
		this.etat = etat;
	}

	public ApplicationBean(Long id, TypeApplication type, EtatApplication etat, Double score, FileBean file,
			OfferBean offer) {
		super();
		this.id = id;
		this.type = type;
		this.etat = etat;
		this.score = score;
		this.file = file;
		this.offer = offer;
	}
	public ApplicationBean() {
		super();
	}
	
	

}
