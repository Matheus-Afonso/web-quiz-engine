package com.mth.webquiz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mth.webquiz.entity.QuizEntity;
import com.mth.webquiz.entity.UserEntity;

// JpaRepository: Possui tudo do CRUD + Ferramentas de sort. Extends do CrudRepository
// CrudRepository: apenas CRUD
@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Integer> {
	Page<QuizEntity> findAllByUser(Pageable pageable, UserEntity user);
}
