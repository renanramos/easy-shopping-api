/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 30/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.service.impl.CompanyService;
import br.com.renanrramos.easyshopping.service.impl.UserService;

/**
 * Test for {@link CompanyController}
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		CompanyController.class
})
public class CompanyControllerTest {

	private final String BASE_URL = "/api/companies";

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Autowired
	private CompanyController companyController;

	@MockBean
	private CompanyService mockService;	

	@MockBean
	private UserService userService;

	@Mock
	private Pageable page;

	private Long companyId = 1L;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper;

	@Before
	public void before() {
		objecMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders
				.standaloneSetup(companyController)
				.build();
		when(userService.isUserEmailAlreadyInUse(anyString())).thenReturn(false);
	}
	
	@Test
	public void shouldCreateNewCompany() throws JsonProcessingException, Exception {
		Company company = getCompanyInstance();
		
		when(mockService.save(any(Company.class))).thenReturn(company);

		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(company))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Teste")))
			.andExpect(jsonPath("$.phone", is("13213321")));

		verify(mockService, times(1)).save(any(Company.class));
	}


	public void shouldReturnBadRequestWhenTryingCreateCompany() throws JsonProcessingException, Exception {
		Company company = new Company();
		when(mockService.save(any(Company.class))).thenReturn(company);

		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(company))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());

		verify(mockService, times(1)).save(any(Company.class));
		
	}

	@Test
	public void shouldReturnListOfCompanies() throws Exception {
		List<Company> companies = new ArrayList<Company>();
		companies.add(getCompanyInstance());
		companies.add(getCompanyInstance());
		companies.add(getCompanyInstance());

		when(mockService.findAllPageable(eq(page), any())).thenReturn(companies);

		mockMvc.perform(get(BASE_URL))
			.andExpect(status().isOk());

		verify(mockService, times(1)).findAllPageable(any(Pageable.class), any());
	}

	@Test
	public void shouldReturnEmptyListOfCompanies() throws Exception {
		List<Company> companies = new ArrayList<Company>();

		when(mockService.findAllPageable(eq(page), any())).thenReturn(companies);

		mockMvc.perform(get(BASE_URL))
			.andExpect(status().isOk());

		verify(mockService, times(1)).findAllPageable(any(Pageable.class), any());
	}

	@Test
	public void shouldReturnCompanyById() throws Exception {
		when(mockService.findById(companyId)).thenReturn(Optional.of(getCompanyInstance()));

		mockMvc.perform(get(BASE_URL + "/" + companyId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Teste")));

		verify(mockService, times(1)).findById(companyId);
	}

	@Test
	public void shoudReturnNotFoundWithAnInvalidCustomerId() throws Exception {
		Company company = new Company();

		when(mockService.findById(2L)).thenReturn(Optional.of(company));

		mockMvc.perform(get(BASE_URL + "/" + companyId))
			.andExpect(status().isNotFound());

		verify(mockService, times(1)).findById(companyId);
	}

	@Test
	public void shouldUpdateCompany() throws Exception {
		Company company = getCompanyInstance();

		when(mockService.findById(companyId)).thenReturn(Optional.of(company));
		when(mockService.save(any(Company.class))).thenReturn(company);

		mockMvc.perform(put(BASE_URL + "/" + companyId)
				.content(objecMapper.writeValueAsString(company))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isAccepted())
			.andExpect(jsonPath("$.id", is(1)));
	}

	@Test
	public void shouldDeleteCompany() throws Exception {
		Company company = getCompanyInstance();
		company.setId(companyId);

		when(mockService.findById(companyId)).thenReturn(Optional.of(company));

		mockMvc.perform(delete(BASE_URL + "/" + companyId))
			.andExpect(status().isOk());

		verify(mockService, times(1)).remove(companyId);
	}

	private static Company getCompanyInstance() {
		Company company = new Company();
		company.setId(1L);
		company.setName("Teste");
		company.setPhone("13213321");
		company.setProfile(Profile.COMPANY);
		company.setRegisteredNumber("1315adsd5");
		company.setEmail("company@mail.com");
		company.setStores(new ArrayList<Store>());
		return company;
	}
}
