package com.c2s.dsif.entities;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="application")
public class Application implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private TypeApplication type;
	@Enumerated(EnumType.STRING)
	private EtatApplication etat;
	private Double score;
	@Column(name="scoreExperience")
	private Double scoreExp;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateCreation;
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Double getScoreExp() {
		return scoreExp;
	}
	public void setScoreExp(Double scoreExp) {
		this.scoreExp = scoreExp;
	}
	//@JsonIgnore
	private Long userId;
	private Long idOffer;
	
	
	public EtatApplication getEtat() {
		return etat;
	}
	public void setEtat(EtatApplication etat) {
		this.etat = etat;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
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

	public Long getIdOffer() {
		return idOffer;
	}
	public void setIdOffer(Long idOffer) {
		this.idOffer = idOffer;
	}

	
	public Application(Long id, TypeApplication type, EtatApplication etat, Double score, Double scoreExp, Long userId,
			Long idOffer, Date dateCreation) {
		super();
		this.id = id;
		this.type = type;
		this.etat = etat;
		this.score = score;
		this.scoreExp = scoreExp;
		this.userId = userId;
		this.idOffer = idOffer;
		this.dateCreation = dateCreation;
	}
	public Application() {
		super();
	}
	

}
