package com.mth.webquiz.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mth.webquiz.entity.QuizEntity;
import com.mth.webquiz.entity.UserEntity;

// JpaRepository: Possui tudo do CRUD + Ferramentas de sort. Extends do CrudRepository
// CrudRepository: apenas CRUD
@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Integer> {
	List<QuizEntity> findByUser(UserEntity user);
	
	Optional<QuizEntity> findByIdAndUser(int id, UserEntity user);
}
