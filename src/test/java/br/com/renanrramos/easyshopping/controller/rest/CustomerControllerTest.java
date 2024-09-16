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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.renanrramos.easyshopping.infra.controller.rest.CustomerController;
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

import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
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

	@MockBean
	private AuthenticationServiceImpl mockAuthentication;

	@Mock
	private Pageable page;

	private Long customerId = 1L;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Customer customer;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(customerController)
				.build();

		customer = EasyShoppingUtil.getCustomerInstance();
	}

	@Test
	public void shouldCreateCustomerSuccessfully() throws JsonProcessingException, Exception {
		when(customerService.save(any(Customer.class))).thenReturn(customer);
		when(customerService.findCustomerByCpf(anyString())).thenReturn(Optional.empty());
		when(mockAuthentication.getName()).thenReturn("customerId");
		mockMvc.perform(post(BASE_URL)
				.content(objectMapper.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is("name")));

		verify(customerService, times(1)).save(any(Customer.class));
	}

	@Test(expected = Exception.class)
	public void saveCustomer_whenCPFAlreadyExist_shouldThrowException() throws JsonProcessingException, Exception {
		Optional<List<Customer>> customerCPFFound = Optional.of(Arrays.asList(customer));
		when(customerService.findCustomerByCpf(anyString())).thenReturn(customerCPFFound);
		mockMvc.perform(post(BASE_URL).content(objectMapper.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
		verify(customerService, never()).save(any(Customer.class));
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
		List<Customer> customers = Arrays.asList(customer, customer, customer);
		when(customerService.findAllPageable(eq(page), any())).thenReturn(customers);

		mockMvc.perform(get(BASE_URL)
				.content(objectMapper.writeValueAsString(customers))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

		verify(customerService, times(1)).findAllPageable(any(Pageable.class), any());
	}

	@Test
	public void getCustomers_withEmptyNameParameter_shouldReturnListOfCustomers() throws JsonProcessingException, Exception {
		List<Customer> customers = Arrays.asList(customer, customer, customer);
		when(customerService.findAllPageable(eq(page), any())).thenReturn(Arrays.asList(customer, customer, customer));

		mockMvc.perform(get(BASE_URL)
				.param("name", "")
				.content(objectMapper.writeValueAsString(customers))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

		verify(customerService, times(1)).findAllPageable(any(Pageable.class), any());
		verify(customerService, never()).findCustomerByName(any(Pageable.class), any());
	}

	@Test
	public void getCustomers_withValidNameParameter_shouldReturnListOfCustomers() throws JsonProcessingException, Exception {
		List<Customer> customers = Arrays.asList(customer, customer, customer);
		when(customerService.findCustomerByName(eq(page), any())).thenReturn(Arrays.asList(customer, customer, customer));

		mockMvc.perform(get(BASE_URL)
				.param("name", "customer")
				.content(objectMapper.writeValueAsString(customers))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

		verify(customerService, never()).findAllPageable(any(Pageable.class), any());
		verify(customerService, times(1)).findCustomerByName(any(Pageable.class), any());
	}

	@Test
	public void shouldReturnEmtpyListOfCustomers() throws JsonProcessingException, Exception {
		List<Customer> customers = new ArrayList<Customer>();

		when(customerService.findAllPageable(eq(page), any())).thenReturn(customers);

		mockMvc.perform(get(BASE_URL))
		.andExpect(status().isOk());

		verify(customerService, times(1)).findAllPageable(any(Pageable.class), any());
	}

	@Test
	public void getCustomerById_withValidCustomerId_shouldReturnCustomerById() throws Exception {
		when(customerService.findCustomerByTokenId(anyString())).thenReturn(Optional.of(customer));

		mockMvc.perform(get(BASE_URL + "/" + customerId))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("name")));

		verify(customerService, times(1)).findCustomerByTokenId(anyString());
	}

	@Test
	public void getCustomerById_withInvalidCustomerId_shouldReturnCustomerById() throws Exception {
		when(customerService.findCustomerByTokenId(anyString())).thenReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL + "/" + customerId))
		.andExpect(status().isNotFound());

		verify(customerService, times(1)).findCustomerByTokenId(anyString());
	}

	@Test(expected = Exception.class)
	public void shouldReturnNotFoundWithAnInvalidCustomerId() throws Exception {

		when(customerService.findCustomerByTokenId(anyString())).thenReturn(null);

		mockMvc.perform(get(BASE_URL + "/" + customerId))
		.andExpect(status().isNotFound());

		verify(customerService, times(1)).findById(customerId);
	}

	@Test
	public void updateCustomer_withValidParameters_shouldUpdateSuccessfully() throws JsonProcessingException, Exception {
		Customer customer = EasyShoppingUtil.getCustomerInstance();
		when(customerService.findCustomerByTokenId(anyString())).thenReturn(Optional.of(customer));
		when(customerService.update(any(Customer.class))).thenReturn(customer);
		when(customerService.findCustomerByCpf(anyString())).thenReturn(Optional.of(Arrays.asList(customer)));
		when(mockAuthentication.getName()).thenReturn("customerId");
		
		mockMvc.perform(patch(BASE_URL + "/1")
				.content(objectMapper.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.name", is("name")));

		verify(customerService, times(1)).update(any(Customer.class));
	}

	@Test(expected = Exception.class)
	public void updateCustomer_whenCustomerNotFound_shouldUpdateSuccessfully() throws JsonProcessingException, Exception {
		Customer customer = EasyShoppingUtil.getCustomerInstance();
		when(customerService.findCustomerByTokenId(anyString())).thenReturn(Optional.empty());
		
		mockMvc.perform(patch(BASE_URL + "/1")
				.content(objectMapper.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(customerService, times(1)).findCustomerByTokenId(anyString());
		verify(customerService, never()).update(any(Customer.class));
	}

	@Test(expected = Exception.class)
	public void updateCustomer_whenCustomerCPFAlreadyExist_shouldUpdateSuccessfully() throws JsonProcessingException, Exception {
		when(customerService.findCustomerByTokenId(anyString())).thenReturn(Optional.of(customer));
		when(customerService.findCustomerByCpf(anyString())).thenReturn(Optional.of(Arrays.asList(customer, customer)));
		
		mockMvc.perform(patch(BASE_URL + "/1")
				.content(objectMapper.writeValueAsString(customer))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(customerService, times(1)).findCustomerByTokenId(anyString());
		verify(customerService, times(1)).findCustomerByCpf(anyString());
		verify(customerService, never()).update(any(Customer.class));
	}

	@Test
	public void removeCustomer_withValidCustomer_shouldRemoveSuccessfully() throws Exception {
		when(customerService.findById(anyLong())).thenReturn(Optional.of(customer));

		mockMvc.perform(delete(BASE_URL + "/1"))
			.andExpect(status().isOk());

		verify(customerService, times(1)).findById(anyLong());
		verify(customerService, times(1)).remove(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeCustomer_withInvalidCustomerId_shouldThowException() throws Exception {
		when(customerService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(delete(BASE_URL + "/1"));

		verify(customerService, times(1)).findById(anyLong());
		verify(customerService, never()).remove(anyLong());
	}
}
