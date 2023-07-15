package com.c2s.dsif.Dto;

import jakarta.persistence.Lob;

public class fileDto {
	 private Long id;
	  private String name;
	  private String type;
	  @Lob
	  private byte[] data;
	  private String content;
	  private userDto user;
	  
	public userDto getUser() {
		return user;
	}
	public void setUser(userDto user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public fileDto(Long id, String name, String type, byte[] data, String content, userDto user) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.data = data;
		this.content = content;
		this.user = user;
	}
	public fileDto() {
		super();
	}
	  

}
