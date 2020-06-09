package com.mth.webquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mth.webquiz.entity.SolvedTimeEntity;

@Repository
public interface SolvedTimeRepository extends JpaRepository<SolvedTimeEntity, Integer> {
	// Nada a adicionar
}
