package com.spring.boot.test.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		
		FilmeDTO obj = service.saveOrUpdate(objDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.ok(id);
	}
	
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<FilmeDTO> find(@PathVariable Long id) throws ObjectNotFoundException {
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<FilmeDTO>> findAll(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody FilmeDTO objDto) {
		service.saveOrUpdate(objDto);
		return ResponseEntity.noContent().build();
	}
}
