package com.mth.webquiz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mth.webquiz.entity.SolvedTimeEntity;
import com.mth.webquiz.entity.UserEntity;

@Repository
public interface SolvedTimeRepository extends JpaRepository<SolvedTimeEntity, Integer> {
	Page<SolvedTimeEntity> findAllByUser(UserEntity user, Pageable pageable);
}
