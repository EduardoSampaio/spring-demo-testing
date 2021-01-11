package com.spring.boot.test.dtos;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmeDTO implements Serializable  {

	private static final long serialVersionUID = 646290239527581866L;
	
	private Long id;
	private String nome;
}
