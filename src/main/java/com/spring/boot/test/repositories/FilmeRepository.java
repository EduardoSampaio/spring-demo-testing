package com.spring.boot.test.repositories;

import com.spring.boot.test.entities.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
