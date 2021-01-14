package com.spring.boot.test.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.test.dtos.FilmeDTO;
import com.spring.boot.test.services.FilmeService;
import com.spring.boot.test.services.exceptions.ObjectNotFoundException;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

	@Autowired
	private FilmeService service;
		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody FilmeDTO objDto){
		
		FilmeDTO saveOrUpdate = service.saveOrUpdate(objDto);
		saveOrUpdate.add(linkTo(FilmeController.class)
				.slash(saveOrUpdate.getId())
				.withSelfRel());
		
		return  ResponseEntity.ok().body(saveOrUpdate);
		
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.ok(linkTo(FilmeController.class).slash(id).withSelfRel());
	}
	
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<FilmeDTO> find(@PathVariable Long id) throws ObjectNotFoundException {
		FilmeDTO filme = service.findById(id);
		
		filme.add(linkTo(FilmeController.class).withRel("Todos os Filmes"));
		return ResponseEntity.ok().body(filme);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<FilmeDTO>> findAll(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody FilmeDTO objDto) {
		FilmeDTO saveOrUpdate = service.saveOrUpdate(objDto);
		saveOrUpdate.add(linkTo(FilmeController.class).slash(saveOrUpdate.getId()).withSelfRel());
		return  ResponseEntity.ok().body(saveOrUpdate);
	}
}
