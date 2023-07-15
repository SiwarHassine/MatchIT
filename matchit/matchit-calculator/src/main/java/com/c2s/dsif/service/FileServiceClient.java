package com.c2s.dsif.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.c2s.dsif.beans.FileBean;

@FeignClient(name = "applicant")
public interface FileServiceClient {
	
	 @GetMapping("/fileContentJSON/{id}")
	 public ResponseEntity<Map<String, String>> getFileAsJson(@PathVariable Long id);
	
	  @GetMapping("/filesContentJSON")
	  public ResponseEntity<List<Map<String, String>>> getAllContentAsJson() throws IOException ;
	 
	 
	  @GetMapping("/fileByUserId/{userId}")
	  public ResponseEntity<Map<String, String>>  getFileByUserId(@PathVariable Long userId) throws IOException;
	  
	  @PutMapping("/files/update/{fileId}")
	  public ResponseEntity<Map<String, String>> updateContentWithSkillsAndExperience(
	          @PathVariable Long fileId,
	          @RequestBody Map<String, String> requestBody) throws IOException;
	  
}
