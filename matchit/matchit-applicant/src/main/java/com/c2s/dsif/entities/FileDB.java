package com.c2s.dsif.entities;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "files")
public class FileDB implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private String name;
	  private String type;
	  @Lob
	  private byte[] data;
	  

	  private Long userId;
	  
	  
	
 
	  public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(columnDefinition = "TEXT")
	    private String content;

	    public FileDB() {
	    }

	
	    public FileDB(Long id,String name, String type, byte[] data, String content, Long userId) {
	        this.id = id;
	    	this.name = name;
	        this.type = type;
	        this.data = data;
	        this.content = content;
	        this.userId = userId;
	    }

	    
	


		public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
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


	
}
