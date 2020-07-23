/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 17/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanrramos.easyshopping.controller.rest.AdministratorController;
import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.service.impl.AdministratorService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		AdministratorController.class
})
public class AdministratorControllerTest {
	
	private final String BASE_URL = "/admin";
	
	private ObjectMapper objecMapper;
	
	@Autowired
	private AdministratorController administratorController;
	
	private MockMvc mockMvc;
	
	@MockBean
	private AdministratorService mockService;

	@Before
	public void before() {
		objecMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders
				.standaloneSetup(administratorController)
				.build();
	}

	@Test
	public void shouldReturnAdminById() throws Exception {
		
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		administrator.setName("Teste");
		administrator.setProfile(Profile.ADMINISTRATOR);
		
		when(mockService.findById(1L)).thenReturn(Optional.of(administrator));
		
		mockMvc.perform(get(BASE_URL + "/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Teste")));
	
		verify(mockService, times(1)).findById(1L);
	}

}
