package com.c2s.dsif.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import com.c2s.dsif.Dto.fileDto;
import com.c2s.dsif.Dto.userDto;
import com.c2s.dsif.entities.FileDB;

public interface FileStorageService {
	
	public Long store(MultipartFile file, Long applicantId) throws IOException;
	public FileDB getFile(Long id);
	//public List<FileDB> getAllFileByApplicant(Long id);
	public Stream<FileDB> getAllFiles();
	public String extractTextFromFile(FileDB fileDB) throws IOException ;
	public List<byte[]> extractImagesFromPDF(FileDB fileDB) throws IOException;
	String getContentFile(Long fileId) throws IOException;
	//List<Map<String, String>> getAllFilesAsJson() throws IOException;
	public Map<String, String> getContentAsJson(Long fileId) throws IOException;
	public List<Map<String, String>> getAllContentAsJson() throws IOException;
	public userDto getUserByFileId(Long fileId) throws FileNotFoundException ;
	public Long getFileIdByUserId(Long userId) throws FileNotFoundException;
	//public Applicant findApplicantByFileId(Long fileId);
	public void updateFile(Long fileId, MultipartFile file) throws IOException ;
	 public Map<String, String> updateContentWithSkillsAndExperience(Long fileId, String skills, String experience) throws IOException;
}
