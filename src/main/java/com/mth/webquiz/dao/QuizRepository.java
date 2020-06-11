package com.mth.webquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mth.webquiz.entity.QuizEntity;

// JpaRepository: Possui tudo do CRUD + Ferramentas de sort. Extends do CrudRepository
// CrudRepository: apenas CRUD
@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Integer> {
	// Nada adicionado
}
