/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 03/08/2020
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.service.impl.CustomerService;
/**
 * Test for {@link CustomerController}
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CustomerController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class CustomerControllerTest {

	private final String BASE_URL = "/api/customers";

	@InjectMocks
	private CustomerController customerController;

	@MockBean
	private CustomerService customerService;

	@Mock
	private Pageable page;

	@Mock
	private Principal principal;

	private Long customerId = 1L;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(customerController)
				.build();
	}

	@Test
	public void shouldCreateCustomerSuccessfully() throws JsonProcessingException, Exception {
		Customer customer = getCustomerInstance();
		when(customerService.save(any(Customer.class))).thenReturn(customer);
		when(customerService.findCustomerByCpf(anyString())).thenReturn(Optional.empty());
		when(principal.getName()).thenReturn("customerId");
		mockMvc.perform(post(BASE_URL)
				.content(objectMapper.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", is("name")));

		verify(customerService, times(1)).save(any(Customer.class));
	}

	@Test
	public void shouldReturnBadRequestWhenTryingCreateCustomer() throws JsonProcessingException, Exception {
		Customer customer = new Customer();

		when(customerService.save(any(Customer.class))).thenReturn(customer);

		mockMvc.perform(post(BASE_URL)
				.content(objectMapper.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());

		verify(customerService, times(1)).save(any(Customer.class));
	}

	@Test
	public void shouldReturnListOfCustomers() throws JsonProcessingException, Exception {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(getCustomerInstance());
		customers.add(getCustomerInstance());
		customers.add(getCustomerInstance());

		when(customerService.findAllPageable(eq(page), any())).thenReturn(customers);

		mockMvc.perform(get(BASE_URL)
				.content(objectMapper.writeValueAsString(customers))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

		verify(customerService, times(1)).findAllPageable(any(Pageable.class), any());
	}

	@Test
	public void shouldReturnEmtpyListOfCustomrers() throws JsonProcessingException, Exception {
		List<Customer> customers = new ArrayList<Customer>();

		when(customerService.findAllPageable(eq(page), any())).thenReturn(customers);

		mockMvc.perform(get(BASE_URL))
		.andExpect(status().isOk());

		verify(customerService, times(1)).findAllPageable(any(Pageable.class), any());
	}

	@Test
	public void shouldReturnCustomerById() throws Exception {
		when(customerService.findCustomerByTokenId(anyString())).thenReturn(Optional.of(getCustomerInstance()));

		mockMvc.perform(get(BASE_URL + "/" + customerId))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("name")));

		verify(customerService, times(1)).findCustomerByTokenId(anyString());
	}

	@Test(expected = Exception.class)
	public void shouldReturnNotFoundWithAnInvalidCustomerId() throws Exception {

		when(customerService.findCustomerByTokenId(anyString())).thenReturn(null);

		mockMvc.perform(get(BASE_URL + "/" + customerId))
		.andExpect(status().isNotFound());

		verify(customerService, times(1)).findById(customerId);
	}

	private static Customer getCustomerInstance() {
		Customer customer = new Customer();
		customer.setCpf("12345684522");
		customer.setEmail("email@mail.com");
		customer.setId(1L);
		customer.setName("name");
		customer.setProfile(Profile.CUSTOMER);
		return customer;
	}
}
