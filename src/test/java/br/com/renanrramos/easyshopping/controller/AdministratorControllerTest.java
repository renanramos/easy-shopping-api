/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 17/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.controller.rest.AdministratorController;
import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.model.form.AdministratorForm;
import br.com.renanrramos.easyshopping.service.impl.AdministratorService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AdministratorControllerTest {
	
	@Mock
	private AdministratorService administratorService;
	
	@Mock
	private AdministratorController administratorController;
	
	@Mock
	private UriComponentsBuilder uriBuilder;
	
	@Mock
	private Administrator administrator;
	
	@Before
	public void before() {

	}

	@Test
	public void shouldCreateAdministrator(){

		AdministratorForm admin = new AdministratorForm();
		admin.setName("New Administrator");

		when(administratorService.save(any(Administrator.class))).thenReturn(administrator);

		administratorController.saveAdministrator(admin, uriBuilder);

		assertThat(administrator.getId()).isNotNull();
	}

}
