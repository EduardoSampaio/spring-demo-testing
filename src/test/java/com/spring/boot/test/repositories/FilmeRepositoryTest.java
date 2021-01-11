package com.spring.boot.test.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.spring.boot.test.entities.Filme;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/scripts/init_data_filmes.sql")
public class FilmeRepositoryTest {

	@Autowired
	private FilmeRepository filmeRepository;
	
	@BeforeEach
	public void setUp() {}

	@Test
	public void testfindbyId() {
		var filmeRetornado = filmeRepository.findById(-2L).get();
		assertEquals("Star wars", filmeRetornado.getNome());
	}

	@Test
	public void testSave() {
		Filme filme = new Filme();
		filme.setId(-1L);
		filme.setNome("Jurassic Park");
		Filme saved = this.filmeRepository.save(filme);
		assertThat(saved).isNotNull();
		assertThat(saved.getNome()).isEqualTo(filme.getNome());
	}
	
	@Test
	public void testUpdate() {
		Filme find = this.filmeRepository.findById(-1L).get();
		assertNotNull(find);
		find.setNome("Lord of the rings");
		Filme updated =  this.filmeRepository.save(find);
		assertThat(updated).isNotNull();
		assertThat(updated.getNome()).isEqualTo("Lord of the rings");
	}
	
	@Test
	public void testDelete() {
		this.filmeRepository.deleteById(-2L);
		Optional<Filme> filmeOptional = this.filmeRepository.findById(-2L);	
		assertThat(filmeOptional.isEmpty()).isTrue();	
	}
}
