package com.c2s.dsif.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.c2s.dsif.entities.FileDB;

import jakarta.transaction.Transactional;
@Transactional
public interface FileDBRepository extends JpaRepository<FileDB, Long> {
	FileDB findByUserId(Long userId);
	
	}
