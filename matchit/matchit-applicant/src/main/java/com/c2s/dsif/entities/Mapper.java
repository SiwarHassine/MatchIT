package com.c2s.dsif.entities;

import org.springframework.stereotype.Component;

import com.c2s.dsif.Dto.fileDto;

@Component
public class Mapper {
	
	 public fileDto toDto(FileDB file) {
	        String filename = file.getName();
	        String fileType = file.getType();
	        String contentfile = file.getContent();
	        byte[] filedata = file.getData();
	        Long id =file.getId();
	        return new fileDto(id,filename, fileType,filedata,contentfile, null);
	    }

	 public FileDB toEntity (fileDto fileDto) {
	        Long id = fileDto.getId();
	    	String filename = fileDto.getName();
	    	String filetype = fileDto.getType();
	    	String filecontent = fileDto.getContent();
	    	byte[] filedata = fileDto.getData();
	    	
	    	Long userId = fileDto.getUser().getId();

	        return new FileDB(id,filename, filetype,filedata,filecontent,userId);
	    }


}
