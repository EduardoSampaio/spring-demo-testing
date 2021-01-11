package com.spring.boot.test.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.boot.test.dtos.FilmeDTO;
import com.spring.boot.test.services.FilmeService;

@WebMvcTest(FilmeController.class)
public class FilmeResources {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmeService service;
    
    private List<FilmeDTO> createListDataMocks() {
		var filmeDto1 = new FilmeDTO();
		filmeDto1.setId(-1L);
		filmeDto1.setNome("Star wars");
		
		var filmeDto2 = new FilmeDTO();
		filmeDto2.setId(-2L);
		filmeDto2.setNome("Back to the future");
		
		return Arrays.asList(filmeDto1, filmeDto2);
	}
    
    @Test
    public void getAllFilmes() throws Exception {
    	when(service.findAll()).thenReturn(createListDataMocks());
    	
    	this.mockMvc.perform(get("/api/filmes")).andDo(print())
        .andExpect(status().isOk());
    }
}
