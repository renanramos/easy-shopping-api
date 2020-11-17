/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 30/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.service.impl.CompanyService;

/**
 * Test for {@link CompanyController}
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CompanyController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class CompanyControllerTest {

	private final String BASE_URL = "/api/companies";

	@InjectMocks
	private CompanyController companyController;

	@MockBean
	private CompanyService companyService;

	@Mock
	private Pageable page;

	@Mock
	private Principal mockPrincipal;

	private Long companyId = 1L;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(companyController)
				.build();
	}

	@Test
	public void shouldCreateNewCompany() throws JsonProcessingException, Exception {
		Company company = getCompanyInstance();

		when(companyService.save(any(Company.class))).thenReturn(company);

		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(company))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Teste")))
		.andExpect(jsonPath("$.phone", is("13213321")));

		verify(companyService, times(1)).save(any(Company.class));
	}


	public void shouldReturnBadRequestWhenTryingCreateCompany() throws JsonProcessingException, Exception {
		Company company = new Company();
		when(companyService.save(any(Company.class))).thenReturn(company);

		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(company))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());

		verify(companyService, times(1)).save(any(Company.class));

	}

	@Test
	public void shouldReturnListOfCompanies() throws Exception {
		List<Company> companies = new ArrayList<Company>();
		companies.add(getCompanyInstance());
		companies.add(getCompanyInstance());
		companies.add(getCompanyInstance());

		when(companyService.findAll(eq(page))).thenReturn(companies);

		mockMvc.perform(get(BASE_URL))
		.andExpect(status().isOk());

		verify(companyService, times(1)).findAll(any(Pageable.class));
	}

	@Test
	public void shouldReturnEmptyListOfCompanies() throws Exception {
		List<Company> companies = new ArrayList<Company>();

		when(companyService.findAll(eq(page))).thenReturn(companies);

		mockMvc.perform(get(BASE_URL))
		.andExpect(status().isOk());

		verify(companyService, times(1)).findAll(any(Pageable.class));
	}

	@Test
	public void shouldReturnEmptyListOfCompanies_withSearchNameParameter() throws Exception {
		List<Company> companies = new ArrayList<Company>();

		when(companyService.findCompanyByName(eq(page), anyString())).thenReturn(companies);

		mockMvc.perform(get(BASE_URL).queryParam("name", "companyName")).andExpect(status().isOk());

		verify(companyService, times(1)).findCompanyByName(any(Pageable.class), anyString());
	}

	@Test
	public void shouldReturnCompanyById() throws Exception {
		when(companyService.findCompanyByTokenId(anyString())).thenReturn(Optional.of(getCompanyInstance()));

		mockMvc.perform(get(BASE_URL + "/" + companyId))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Teste")));

		verify(companyService, times(1)).findCompanyByTokenId(anyString());
	}

	@Test(expected = Exception.class)
	public void shoudReturnNotFoundWithAnInvalidCompanyTokenId() throws Exception {
		new Company();

		when(companyService.findCompanyByTokenId(anyString())).thenReturn(null);

		mockMvc.perform(get(BASE_URL + "/" + companyId))
		.andExpect(status().isNotFound());

		verify(companyService, times(1)).findCompanyByTokenId(anyString());
	}

	@Test
	public void shouldUpdateCompany() throws Exception {
		Company company = getCompanyInstance();

		when(companyService.findCompanyByTokenId(anyString())).thenReturn(Optional.of(company));
		when(companyService.save(any(Company.class))).thenReturn(company);

		mockMvc.perform(patch(BASE_URL + "/" + companyId)
				.content(objecMapper.writeValueAsString(company))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted())
		.andExpect(jsonPath("$.id", is(1)));
	}

	@Test
	public void shouldDeleteCompany() throws Exception {
		Company company = getCompanyInstance();
		company.setId(companyId);

		when(companyService.findById(anyLong())).thenReturn(Optional.of(company));

		mockMvc.perform(delete(BASE_URL + "/" + companyId))
		.andExpect(status().isOk());

		verify(companyService, times(1)).remove(companyId);
	}

	private static Company getCompanyInstance() {
		Company company = new Company();
		company.setId(1L);
		company.setName("Teste");
		company.setPhone("13213321");
		company.setProfile(Profile.COMPANY);
		company.setRegisteredNumber("1315adsd5");
		company.setEmail("company@mail.com");
		return company;
	}
}
