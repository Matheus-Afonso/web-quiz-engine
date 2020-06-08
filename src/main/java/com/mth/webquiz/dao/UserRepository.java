package com.mth.webquiz.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mth.webquiz.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	// Para usar com Spring Security
	Optional<UserEntity> findByEmail(String email);
}
