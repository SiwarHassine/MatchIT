package com.c2s.dsif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.c2s.dsif.entities.Cv;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {

}

