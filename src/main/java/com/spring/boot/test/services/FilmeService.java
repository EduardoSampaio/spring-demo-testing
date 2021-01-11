package com.spring.boot.test.services;

import java.util.List;

import com.spring.boot.test.dtos.FilmeDTO;

public interface FilmeService {
	
	FilmeDTO saveOrUpdate(FilmeDTO obj);
	void delete(Long id);
	FilmeDTO findById(Long id);
	List<FilmeDTO> findAll();
}
