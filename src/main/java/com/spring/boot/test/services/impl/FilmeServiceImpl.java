package com.spring.boot.test.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.test.dtos.FilmeDTO;
import com.spring.boot.test.entities.Filme;
import com.spring.boot.test.repositories.FilmeRepository;
import com.spring.boot.test.services.FilmeService;
import com.spring.boot.test.services.exceptions.ObjectNotFoundException;

@Service
public class FilmeServiceImpl implements FilmeService {
	
	@Autowired
	private FilmeRepository repository;

	@Override
	public FilmeDTO saveOrUpdate(FilmeDTO dto) {
		Filme filme = BeanUtils.instantiateClass(Filme.class);
		BeanUtils.copyProperties(dto, filme);
		
		Filme saved = repository.save(filme);	
		BeanUtils.copyProperties(saved, dto);
		return dto;
	}

	@Override
	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);	
	}

	@Override
	public FilmeDTO findById(Long id) {
		Optional<Filme> obj = repository.findById(id);
		FilmeDTO dto = BeanUtils.instantiateClass(FilmeDTO.class);
		
		
		obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Filme.class.getName()));
		
		BeanUtils.copyProperties(obj.get(), dto);
		
		return dto;
	}

	@Override
	public List<FilmeDTO> findAll() {
		List<Filme> filmes = repository.findAll();
		List<FilmeDTO> dtos = new ArrayList<>();
		
		for (Filme filme : filmes) {
			FilmeDTO dto = BeanUtils.instantiateClass(FilmeDTO.class);
			BeanUtils.copyProperties(filme, dto);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
}
