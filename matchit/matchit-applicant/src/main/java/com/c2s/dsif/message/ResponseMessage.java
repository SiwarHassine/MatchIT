package com.c2s.dsif.message;

public class ResponseMessage {
	  private String message;
	  private Long cvId;

	  public ResponseMessage(String message, Long cvId) {
	        this.message = message;
	        this.cvId = cvId;
	    }

	  public Long getCvId() {
		return cvId;
	}

	public void setCvId(Long cvId) {
		this.cvId = cvId;
	}

	public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }

}
