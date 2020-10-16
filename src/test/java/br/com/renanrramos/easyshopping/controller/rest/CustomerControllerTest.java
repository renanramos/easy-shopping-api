/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 03/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
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
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.service.impl.CustomerService;
/**
 * Test for {@link CustomerController}
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		CustomerController.class
})
public class CustomerControllerTest {

	private final String BASE_URL = "/api/customers";

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Autowired
	private CustomerController customerController;

	@MockBean
	private CustomerService mockService;

	@Mock
	private Pageable page;

	private Long customerId = 1L;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@Before
	public void before() {
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders
				.standaloneSetup(customerController)
				.build();
	}

	@Test
	public void shouldCreateCustomerSuccessfully() throws JsonProcessingException, Exception {
		Customer customer = getCustomerInstance();

		when(mockService.save(any(Customer.class))).thenReturn(customer);

		mockMvc.perform(post(BASE_URL)
				.content(objectMapper.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", is("name")));

		verify(mockService, times(1)).save(any(Customer.class));
	}


	public void shouldReturnBadRequestWhenTryingCreateCustomer() throws JsonProcessingException, Exception {
		Customer customer = new Customer();

		when(mockService.save(any(Customer.class))).thenReturn(customer);

		mockMvc.perform(post(BASE_URL)
				.content(objectMapper.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());

		verify(mockService, times(1)).save(any(Customer.class));
	}

	@Test
	public void shouldReturnListOfCustomers() throws JsonProcessingException, Exception {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(getCustomerInstance());
		customers.add(getCustomerInstance());
		customers.add(getCustomerInstance());

		when(mockService.findAllPageable(eq(page), any())).thenReturn(customers);

		mockMvc.perform(get(BASE_URL)
				.content(objectMapper.writeValueAsString(customers))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		verify(mockService, times(1)).findAllPageable(any(Pageable.class), any());
	}

	@Test
	public void shouldReturnEmtpyListOfCustomrers() throws JsonProcessingException, Exception {
		List<Customer> customers = new ArrayList<Customer>();

		when(mockService.findAllPageable(eq(page), any())).thenReturn(customers);

		mockMvc.perform(get(BASE_URL))
			.andExpect(status().isOk());

		verify(mockService, times(1)).findAllPageable(any(Pageable.class), any());	
	}

	@Test
	public void shouldReturnCustomerById() throws Exception {
		when(mockService.findById(customerId)).thenReturn(Optional.of(getCustomerInstance()));

		mockMvc.perform(get(BASE_URL + "/" + customerId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("name")));

		verify(mockService, times(1)).findById(customerId);
	}

	@Test
	public void shouldReturnNotFoundWithAnInvalidCustomerId() throws Exception {
		Customer customer = new Customer();
		
		when(mockService.findById(2L)).thenReturn(Optional.of(customer));

		mockMvc.perform(get(BASE_URL + "/" + customerId))
			.andExpect(status().isNotFound());

		verify(mockService, times(1)).findById(customerId);
	}

	private static Customer getCustomerInstance() {
		Customer customer = new Customer();
//		customer.setAddress(new HashSet<>());
		customer.setCpf("12345684522");
		customer.setEmail("email@mail.com");
		customer.setId(1L);
		customer.setName("name");
		customer.setProfile(Profile.CUSTOMER);
		return customer;
	}
}
