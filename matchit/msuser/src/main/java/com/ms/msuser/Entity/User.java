package com.ms.msuser.Entity;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name="users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String name;
	@JsonFormat(pattern="yyyy-MM-dd")
    private Date datenaisance;
    private long phone;
    private String password;
    private String email;
    private Long active;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private RoleEnum roles;
    @Enumerated(EnumType.STRING)
    private Types candidattype;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDatenaisance() {
		return datenaisance;
	}
	public void setDatenaisance(Date datenaisance) {
		this.datenaisance = datenaisance;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getActive() {
		return active;
	}
	public void setActive(Long active) {
		this.active = active;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public RoleEnum getRoles() {
		return roles;
	}
	public void setRoles(RoleEnum roles) {
		this.roles = roles;
	}
	public Types getCandidattype() {
		return candidattype;
	}
	public void setCandidattype(Types candidattype) {
		this.candidattype = candidattype;
	}
	public User(long id, String firstname, String name, Date datenaisance, long phone, String password, String email,
			Long active, Genre genre, RoleEnum roles, Types candidattype) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.name = name;
		this.datenaisance = datenaisance;
		this.phone = phone;
		this.password = password;
		this.email = email;
		this.active = active;
		this.genre = genre;
		this.roles = roles;
		this.candidattype = candidattype;
	}
	public User() {
		super();
	}
    
    
}

