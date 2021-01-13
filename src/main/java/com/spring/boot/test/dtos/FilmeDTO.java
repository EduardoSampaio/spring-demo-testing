package com.spring.boot.test.dtos;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class FilmeDTO extends RepresentationModel<FilmeDTO> implements Serializable  {

	private static final long serialVersionUID = 646290239527581866L;
	
	private Long id;
	private String nome;
}
