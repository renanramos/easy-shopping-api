/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.form.AddressForm;
import br.com.renanrramos.easyshopping.service.impl.AddressService;
import br.com.renanrramos.easyshopping.service.impl.CustomerService;

/**
 * 
 * @author renan.ramos
 * Test for {@link AddressController }
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		AddressController.class
})
public class AddressControllerTest {
	
	private final String BASE_URL = "/addresses";

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Autowired
	private AddressController addressController;
	
	@MockBean
	private AddressService mockService;
	
	@MockBean
	private CustomerService customerService;

	private Optional<Customer> customerOptional = Optional.of(getCustomer());

	private MockMvc mockMvc;
	
	private ObjectMapper objecMapper;
	
	private Long customerId;

	private Long addressId;
	
	@Before
	public void before() {
		objecMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders
				.standaloneSetup(addressController)
				.build();
	}

	@Test
	public void shouldCreateNewAddressSuccessfully() throws JsonProcessingException, Exception {

		Address address = getAddress();
		AddressForm addressForm = new AddressForm();
		addressForm.setCustomerId(1L);
		
		when(customerService.findById(1L)).thenReturn(customerOptional);
		when(mockService.save(any(Address.class))).thenReturn(address);
		
		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(addressForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.cep", is("cep")));
				
		verify(mockService, times(1)).save(any(Address.class));
	}
	
	@Test
	public void shouldReturnNotFoundWhenHasNullCustomerId() throws JsonProcessingException, Exception {

		AddressForm addressForm = new AddressForm();
		addressForm.setCustomerId(null);

		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(addressForm)));
		
		verify(mockService, never()).save(any(Address.class));
	}
	
	@Test
	public void shouldReturnNotFoundWhenCustomerDoesntExist() throws JsonProcessingException, Exception {
		AddressForm addressForm = new AddressForm();
		addressForm.setCustomerId(1L);

		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(addressForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(mockService, never()).save(any(Address.class));
	}

	@Test
	public void shouldReturnAListOfAddresses() throws Exception {
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(getAddress());
		addresses.add(getAddress());
		addresses.add(getAddress());

		when(mockService.findAll()).thenReturn(addresses);

		mockMvc.perform(get(BASE_URL))
			.andExpect(status().isOk());

		verify(mockService, times(1)).findAll();
		assertThat(mockService.findAll().size(), equalTo(3));
	}

	@Test
	public void shouldReturnEmptyListOfAddress() throws Exception {
		List<Address> addresses = new ArrayList<Address>();

		when(mockService.findAll()).thenReturn(addresses);

		mockMvc.perform(get(BASE_URL))
			.andExpect(status().isOk());

		verify(mockService, times(1)).findAll();
		assertTrue(mockService.findAll().isEmpty());
	}

	@Test
	public void shouldReturnAddressById() throws Exception {
		Address address = getAddress();

		when(mockService.findById(1L)).thenReturn(Optional.of(address));

		mockMvc.perform(get(BASE_URL + "/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.streetName", is("streetName")));

		verify(mockService, times(1)).findById(1L);
	}
	
	@Test
	public void shouldReturnNotFoundWhenAddressIdIsInvalid() throws Exception {
		Address address = getAddress();

		when(mockService.findById(2L)).thenReturn(Optional.of(address));

		mockMvc.perform(get(BASE_URL + "/1"))
			.andExpect(status().isNotFound());
	}
	
	private static Address getAddress() {
		Address address = new Address();
		address.setId(1L);
		address.setCep("cep");
		address.setDistrict("district");
		address.setNumber(123L);
		address.setState("state");
		address.setStreetName("streetName");
		address.setCustomer(getCustomer());
		return address;
	}
	
	private static Customer getCustomer() {
		Customer customer = new Customer();
		customer.setCpf("cpf");
		customer.setId(1L);
		customer.setEmail("email@mail.com");
		customer.setName("customer");
		return customer;
	}
}
