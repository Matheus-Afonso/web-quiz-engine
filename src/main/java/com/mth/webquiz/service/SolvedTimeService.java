package com.mth.webquiz.service;

import org.springframework.data.domain.Page;

import com.mth.webquiz.entity.SolvedTimeEntity;
import com.mth.webquiz.entity.UserEntity;

public interface SolvedTimeService {
	
	void save(SolvedTimeEntity timeEntity);
	
	Page<SolvedTimeEntity> findAllbyUser(int page, int pageSize, String sortBy, UserEntity user);
}
