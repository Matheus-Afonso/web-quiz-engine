package com.mth.webquiz.service;

import org.springframework.data.domain.Page;

import com.mth.webquiz.entity.SolvedTimeEntity;

public interface SolvedTimeService {
	
	void save(SolvedTimeEntity timeEntity);
	
	Page<SolvedTimeEntity> findAll(int page, int pageSize, String sortBy);
}
