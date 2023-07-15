package com.c2s.dsif.beans;

import jakarta.persistence.Lob;

public class FileBean { 
	
	private Long id;
	private String name;
	private String type;
	@Lob
	private byte[] data;
	
	private String content;
	private Long userId;

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public FileBean() {
    }

    public FileBean(String name, String type, byte[] data, String content) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.content = content;
    }
    public FileBean(String name, String type, byte[] data, String content, Long userId) {
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
