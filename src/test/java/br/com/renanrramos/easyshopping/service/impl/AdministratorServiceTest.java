/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 17/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import br.com.renanrramos.easyshopping.controller.AdministratorController;
import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.model.builder.AdministratorBuilder;
import br.com.renanrramos.easyshopping.model.dto.AdministratorDTO;

/**
 * @author renan.ramos
 *
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AdministratorServiceTest {
	
	@InjectMocks
	AdministratorController administratorController;
	
	@Mock
	private AdministratorService myService;
	
	@Before
	public void before() {
	}
	
	@Test
	public void shouldReturnAListOfAdministrators() {
		
		List<Administrator> administrators = new ArrayList<Administrator>();
		administrators.add(AdministratorBuilder.builder().withName("Teste1").build());
		
		
		when(myService.findAll()).thenReturn(administrators);
		
		ResponseEntity<List<AdministratorDTO>> administratorsExpected = administratorController.getAdministrators();
		
		List<AdministratorDTO> administratorDTOList = AdministratorDTO.converterAdministratorListToAdministratorDTO(administrators);
		
		
		assertEquals(administratorsExpected.getBody().size(), administratorDTOList.size());
	}
	
	@Test
	public void shouldReturnAnEmptyListOfAdministrators() {
		List<Administrator> administrators = new ArrayList<Administrator>();
		administrators.add(new Administrator());
		when(myService.findAll()).thenReturn(administrators);
		
		ResponseEntity<List<AdministratorDTO>> administratorsExpected = administratorController.getAdministrators();

		assertEquals(administratorsExpected.getBody(), nullValue());
	}
}
