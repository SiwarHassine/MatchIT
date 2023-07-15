package com.c2s.dsif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.c2s.dsif.entities.Skills;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long>{

}
