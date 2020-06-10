package com.mth.webquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mth.webquiz.dao.SolvedTimeRepository;
import com.mth.webquiz.entity.SolvedTimeEntity;
import com.mth.webquiz.entity.UserEntity;

@Service
public class SolvedTimeServiceImpl implements SolvedTimeService {
	
	@Autowired
	SolvedTimeRepository timeRepository;
	
	@Override
	public void save(SolvedTimeEntity timeEntity) {
		timeRepository.save(timeEntity);
	}

	@Override
	public Page<SolvedTimeEntity> findAllbyUser(int page, int pageSize, String sortBy, UserEntity user) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
		return timeRepository.findAllByUser(user, pageable);
	}

}
