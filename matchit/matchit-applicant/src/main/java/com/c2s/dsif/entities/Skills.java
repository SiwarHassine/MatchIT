package com.c2s.dsif.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToMany(mappedBy = "skills")
    private List<Cv> cvs;

	public Skills() {
		super();
	}

	public Skills(Long id, String nom, List<Cv> cvs) {
		super();
		this.id = id;
		this.nom = nom;
		this.cvs = cvs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Cv> getCvs() {
		return cvs;
	}

	public void setCvs(List<Cv> cvs) {
		this.cvs = cvs;
	}

    //getters and setters
}