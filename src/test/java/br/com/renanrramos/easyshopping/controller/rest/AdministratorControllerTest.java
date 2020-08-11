/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 17/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.service.impl.AdministratorService;


/**
 * Test for {@link AdministratorController}
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		AdministratorController.class
})
public class AdministratorControllerTest {
	
	private final String BASE_URL = "/api/admin";

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private ObjectMapper objecMapper;
	
	@Autowired
	private AdministratorController administratorController;
	
	private MockMvc mockMvc;
	
	@MockBean
	private AdministratorService mockService;

	@Mock
	private Pageable page;

	@Before
	public void before() {
		objecMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders
				.standaloneSetup(administratorController)
				.build();
	}

	@Test
	public void shouldCreatNewAdministrator() throws JsonProcessingException, Exception {
		Administrator administrator = getAdministratorInstance();
		
		when(mockService.save(any(Administrator.class))).thenReturn(administrator);
		
		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(administrator))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))				
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Teste")));
		verify(mockService, times(1)).save(any(Administrator.class));
		
	}
	
	@Test
	public void shouldReturnNoContentWhenTryingCreateAdministrator() throws JsonProcessingException, Exception {
		Administrator administrator = getAdministratorInstance();
		administrator.setId(null);
		when(mockService.save(any(Administrator.class))).thenReturn(administrator);
		
		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(administrator))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))				
				.andExpect(status().isNoContent());
		verify(mockService, times(1)).save(any(Administrator.class));
	}
	
	@Test
	public void shouldReturnListOfAdministrators() throws Exception {
		List<Administrator> admins = new ArrayList<Administrator>();
		admins.add(getAdministratorInstance());
		admins.add(getAdministratorInstance());
		admins.add(getAdministratorInstance());
		
		when(mockService.findAllPageable(eq(page), any())).thenReturn(admins);
		
		mockMvc.perform(get(BASE_URL))
			.andExpect(status().isOk());
		
		verify(mockService, times(1)).findAllPageable(any(Pageable.class), any());
	}
	
	@Test
	public void shouldReturnEmptyListAdministrators() throws Exception {
		List<Administrator> admins = new ArrayList<Administrator>();
		
		when(mockService.findAllPageable(eq(page), any())).thenReturn(admins);
		
		mockMvc.perform(get(BASE_URL))
			.andExpect(status().isOk());
		
		verify(mockService, times(1)).findAllPageable(any(Pageable.class), any());
	}
	
	@Test
	public void shouldReturnAdminById() throws Exception {
		
		Administrator administrator = getAdministratorInstance();
		
		when(mockService.findById(1L)).thenReturn(Optional.of(administrator));
		
		mockMvc.perform(get(BASE_URL + "/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Teste")));
	
		verify(mockService, times(1)).findById(1L);
	}
	
	@Test
	public void shouldReturnNotFoundWithAdministratorWithInvalidId() throws Exception {
		Administrator administrator = new Administrator();

		when(mockService.findById(2L)).thenReturn(Optional.ofNullable(administrator));
		
		mockMvc.perform(get(BASE_URL + "/1"))
			.andExpect(status().isNotFound());
	
		verify(mockService, times(1)).findById(1L);
	}
	
	@Test
	public void shouldUpdateAdministrator() throws JsonProcessingException, Exception {
		Administrator administrator = getAdministratorInstance();
		
		when(mockService.findById(1L)).thenReturn(Optional.of(administrator));
		when(mockService.save(any(Administrator.class))).thenReturn(administrator);
		
		mockMvc.perform(put(BASE_URL + "/1")
				.content(objecMapper.writeValueAsString(administrator))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted())
		.andExpect(jsonPath("$.id", is(1)));
		
	}
	
	@Test
	public void shouldDeleteAdministrator() throws Exception {
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		
		when(mockService.findById(1L)).thenReturn(Optional.of(administrator));
		
		mockMvc.perform(delete(BASE_URL + "/1"))
			.andExpect(status().isOk());
		
		verify(mockService, times(1)).remove(1L);
	}

	private static Administrator getAdministratorInstance() {
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		administrator.setName("Teste");
		administrator.setProfile(Profile.ADMINISTRATOR);
		return administrator;
	}

}
