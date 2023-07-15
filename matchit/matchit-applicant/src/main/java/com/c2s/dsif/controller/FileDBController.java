package com.c2s.dsif.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.c2s.dsif.Dto.fileDto;
import com.c2s.dsif.Dto.userDto;
import com.c2s.dsif.entities.FileDB;
import com.c2s.dsif.message.ResponseMessage;
import com.c2s.dsif.repositories.FileDBRepository;
import com.c2s.dsif.services.FileStorageService;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;



@Controller
public class FileDBController {
	
	@Autowired
	private FileDBRepository repository;
	@Autowired
	private FileStorageService service;
	
	@PostMapping("/upload/{userId}")
	public ResponseEntity<ResponseMessage> uploadFile(@PathVariable(value = "userId") Long userId,
	        @RequestParam("file") MultipartFile file) {
	    String message = "";
	    Long cvId = null; // Déclaration de la variable cvId avec une valeur par défaut
	    try {
	        cvId = service.store(file, userId); // Assignation de la valeur réelle à cvId
	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, cvId));
	    } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        System.out.println(e);
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, cvId));
	    }
	}
	
	@PutMapping("/updateContent/{fileId}")
    public ResponseEntity<String> updateFileContent(
            @PathVariable Long fileId,
            @RequestParam("file") MultipartFile file
    ) {	try {
        	service.updateFile(fileId, file);
            return ResponseEntity.ok("File content updated successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating file content.");
        }  }
	
	 @GetMapping("/file/{id}/user")
	    public ResponseEntity<userDto> getUserByFileId(@PathVariable Long id) throws FileNotFoundException {
	    	userDto userDTO = service.getUserByFileId(id);
	    	System.out.println(userDTO.getEmail());
	        return new ResponseEntity<userDto>(userDTO, HttpStatus.OK);
	    }
	    
	    @GetMapping("/file/{id}/userId")
	    public Long getUserIdByFileId(@PathVariable Long id) throws FileNotFoundException {
	    	userDto userDTO = service.getUserByFileId(id);
	        return userDTO.getId();
	    }
	    
	  @GetMapping("/files/{id}")
	  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
	    FileDB fileDB = service.getFile(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
	        .body(fileDB.getData());
	  }
	  
	  @GetMapping("/files/{id}/text")
	  public ResponseEntity<String> getFileText(@PathVariable Long id) {
	      FileDB fileDB = service.getFile(id);
	      String text;
	      try {
	          text = service.extractTextFromFile(fileDB);
	      } catch (IOException e) {
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while extracting text from file.");
	      }
	      return ResponseEntity.ok()
	              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
	              .body(text);
	  }
	  
	  @GetMapping("/file/{fileId}/content")
	  public ResponseEntity<String> getFileContent(@PathVariable Long fileId) throws IOException {
	      String content = service.getContentFile(fileId);
	      return ResponseEntity.ok(content);
	  }

	  @GetMapping("/fileContentJSON/{id}")
	    public ResponseEntity<Map<String, String>> getFileAsJson(@PathVariable Long id) throws IOException {
	        Map<String, String> contentMap = service.getContentAsJson(id);
	        return ResponseEntity.ok(contentMap);
	    }
	  
	  @GetMapping("/fileByUserId/{userId}")
	  public ResponseEntity<Long> getFileByUserId(@PathVariable Long userId) throws IOException {
	      Long jsonId = service.getFileIdByUserId(userId);
	      return ResponseEntity.ok(jsonId);
	  }
	  
	  @GetMapping("/filesContentJSON")
	  public ResponseEntity<List<Map<String, String>>> getAllContentAsJson() throws IOException {
	      List<Map<String, String>> contents = service.getAllContentAsJson();
	      return ResponseEntity.ok(contents);
	  }
	  
	//  @GetMapping("/filesContent")
	  //public ResponseEntity<Stream<FileDB>> getAllFiles() throws IOException {
		//  Stream<FileDB> contents = service.getAllFiles();
	      //return ResponseEntity.ok(contents);
	 // }
	  
	  
	  @PutMapping("/files/update/{fileId}")
	  public ResponseEntity<Map<String, String>> updateContentWithSkillsAndExperience(
	          @PathVariable Long fileId,
	          @RequestBody Map<String, String> requestBody) throws IOException {
	      String contents = (String)requestBody.get("contents");
	      String experience = (String)requestBody.get("experience");

	      Map<String, String> updatedContent = service.updateContentWithSkillsAndExperience(fileId, contents, experience);
	      return ResponseEntity.ok(updatedContent);
	  }
	  
	  
	  



}
