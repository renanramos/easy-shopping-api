/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

	private Optional<Customer> customerOptional = Optional.of(new Customer());

	private MockMvc mockMvc;
	
	private ObjectMapper objecMapper;
	
	private Long customerId;
	
	@Before
	public void before() {
		objecMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders
				.standaloneSetup(addressController)
				.build();
	}

	@Test
	public void shouldCreateNewAddress() throws JsonProcessingException, Exception {

		Address address = getAddress();

		when(customerService.findById(eq(customerId))).thenReturn(customerOptional);
		when(mockService.save(any(Address.class))).thenReturn(address);
		
		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(address))
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
