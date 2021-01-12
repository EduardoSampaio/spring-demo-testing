package com.spring.boot.test.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.test.dtos.FilmeDTO;
import com.spring.boot.test.services.FilmeService;
import com.spring.boot.test.services.exceptions.ObjectNotFoundException;

@WebMvcTest(FilmeController.class)
public class FilmeControllerTest {

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
	public void testGetAllFilmes() throws Exception {
		when(service.findAll()).thenReturn(createListDataMocks());

		// Forma 1

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/filmes").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[{\"id\":-1,\"nome\":\"Star wars\"},{\"id\":-2,\"nome\":\"Back to the future\"}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

		// Forma 2

		this.mockMvc.perform(get("/api/filmes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(2))
				.andExpect(jsonPath("$[0].id").value("-1")).andExpect(jsonPath("$[0].nome").value("Star wars"))
				.andExpect(jsonPath("$[1].id").value("-2"))
				.andExpect(jsonPath("$[1].nome").value("Back to the future"));
	}

	@Test
	public void testGetById() throws Exception {
		when(service.findById(-1L)).thenReturn(createListDataMocks().get(0));

		this.mockMvc.perform(get("/api/filmes/-1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("-1"))
				.andExpect(jsonPath("$.nome").value("Star wars"));
	}
	
	@Test
	public void testGetById$NotFound() throws Exception {
		when(service.findById(-10L)).thenThrow(new ObjectNotFoundException(""));

		this.mockMvc.perform(get("/api/filmes/-10")
				.contentType(MediaType.APPLICATION_JSON))	
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Não encontrado"));
	}
	
	@Test
	public void testSave() throws Exception {
		var filmeDto1 = new FilmeDTO();
		filmeDto1.setId(-1L);
		filmeDto1.setNome("Star wars");

		when(service.saveOrUpdate(Mockito.any(FilmeDTO.class))).thenReturn(filmeDto1);
		
		String expected = "{\"id\":-1,\"nome\":\"Star wars\"}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/filmes")
				.accept(MediaType.APPLICATION_JSON).content(expected)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
	
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/api/filmes/-1",
				response.getHeader(HttpHeaders.LOCATION));
	}
	
	@Test
	public void testUpdate() throws Exception {
		var filmeDto1 = new FilmeDTO();
		filmeDto1.setId(-1L);
		filmeDto1.setNome("Star wars");

		when(service.saveOrUpdate(Mockito.any(FilmeDTO.class))).thenReturn(filmeDto1);
		
		String expected = "{\"id\":-1,\"nome\":\"Star wars\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/filmes")
				.accept(MediaType.APPLICATION_JSON).content(expected)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
	
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}
}
