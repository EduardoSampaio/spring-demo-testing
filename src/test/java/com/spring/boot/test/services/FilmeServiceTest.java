package com.spring.boot.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.spring.boot.test.dtos.FilmeDTO;
import com.spring.boot.test.entities.Filme;
import com.spring.boot.test.repositories.FilmeRepository;
import com.spring.boot.test.services.exceptions.ObjectNotFoundException;
import com.spring.boot.test.services.impl.FilmeServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FilmeServiceTest {

	@InjectMocks
	private FilmeServiceImpl service;

	@Mock
	private FilmeRepository repository;

	private Filme createOneDataMocks() {
		var filme1 = new Filme();
		filme1.setId(-1L);
		filme1.setNome("Star Wars");
		return filme1;
	}

	@BeforeEach
	public void setUp() {
		when(repository.findById(Mockito.anyLong())).
		thenReturn(Optional.of(createOneDataMocks()));
		
		when(repository.save(Mockito.any(Filme.class))).
		thenReturn(createOneDataMocks());
		
		when(repository.findById(-1L))
		.thenReturn(Optional.of(createOneDataMocks()));
		
		when(repository.findById(-100L)).thenReturn(Optional.empty());
	}

	@Test
	public void when_findById_return_successful() {
		FilmeDTO findById = service.findById(-1L);
		assertThat(findById).isNotNull();
	}
	
	@Test
	public void when_findById_return_exception() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.findById(-100L);
		});
		
		assertEquals("Objeto não encontrado! Id: -100, Tipo: com.spring.boot.test.entities.Filme", exception.getMessage());
	}

	@Test
	public void when_save_return_successful() {
		var filmeDto = new FilmeDTO();
		filmeDto.setId(-1L);
		filmeDto.setNome("Star Wars");
		
		FilmeDTO saved = service.saveOrUpdate(filmeDto);
		assertThat(saved).isNotNull();
		assertThat(saved.getNome()).isEqualTo("Star Wars");
		verify(repository).save(Mockito.any(Filme.class));	
	}
	
	@Test
	public void  when_delete_return_successful() {
		service.delete(-1L);	
		verify(repository,times(1)).deleteById(-1L);
	}
}
