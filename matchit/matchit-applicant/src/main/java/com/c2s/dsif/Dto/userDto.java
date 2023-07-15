package com.c2s.dsif.Dto;

import java.util.Date;


public class userDto {

	private long id;
    private String firstname;
    private String name;
    private Date datenaisance;
    private long phone;
    private String password;
    private String email;
    private Long active;
	public Long getId() {
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
	public userDto(long id, String firstname, String name, Date datenaisance, long phone, String password, String email,
			Long active) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.name = name;
		this.datenaisance = datenaisance;
		this.phone = phone;
		this.password = password;
		this.email = email;
		this.active = active;
	}
	public userDto() {
		super();
	}
    
    
}
