/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 27/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.renanrramos.easyshopping.infra.controller.rest.AddressController;
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

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.AddressForm;
import br.com.renanrramos.easyshopping.service.impl.AddressService;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.CustomerService;

/**
 *
 * @author renan.ramos
 * Test for {@link AddressController }
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AddressController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class AddressControllerTest {

	private final String BASE_URL = "/api/addresses";

	@InjectMocks
	private AddressController addressController;

	@MockBean
	private AddressService addressService;

	@MockBean
	private CustomerService customerService;

	@MockBean
	private AuthenticationServiceImpl mockAuthentication;

	@Mock
	private Pageable page;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	private Long customerId;

	private Long addressId;

	private Address address = new Address();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();

		when(mockAuthentication.getName()).thenReturn("customerId");

		address = EasyShoppingUtil.getAddressInstance();
	}

	@Test
	public void shouldCreateNewAddressSuccessfully() throws JsonProcessingException, Exception {
		when(addressService.save(any(Address.class))).thenReturn(address);

		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(address))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.cep", is("cep"))).andExpect(jsonPath("$.district", is("district")));

		verify(addressService, times(1)).save(any(Address.class));
	}

	@Test
	public void shouldReturnNotFoundWhenHasNullCustomerId() throws JsonProcessingException, Exception {
		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(address)));

		verify(addressService, never()).save(any(Address.class));
	}

	@Test
	public void shouldReturnAListOfAddresses() throws Exception {
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address);
		addresses.add(address);
		addresses.add(address);

		when(addressService.findAllPageable(eq(page), anyString())).thenReturn(addresses);

		mockMvc.perform(get(BASE_URL))
		.andExpect(status().isOk());

		verify(addressService, times(1)).findAllPageable(any(Pageable.class), anyString());
	}

	@Test
	public void shouldReturnEmptyListOfAddress() throws Exception {
		List<Address> addresses = new ArrayList<Address>();

		when(addressService.findAllPageable(eq(page), anyString())).thenReturn(addresses);

		mockMvc.perform(get(BASE_URL))
		.andExpect(status().isOk());

		verify(addressService, times(1)).findAllPageable(any(Pageable.class), anyString());
	}

	@Test
	public void shouldReturnEmptyListOfAddress_withStreeNameSearchParameter() throws Exception {
		List<Address> addresses = new ArrayList<Address>();

		when(addressService.findAddressByStreetName(eq(page), anyString())).thenReturn(addresses);

		mockMvc.perform(get(BASE_URL).queryParam("streetName", "streetName")).andExpect(status().isOk());

		verify(addressService, times(1)).findAddressByStreetName(any(Pageable.class), anyString());
	}

	@Test
	public void shouldReturnAddressById() throws Exception {
		when(addressService.findById(1L)).thenReturn(Optional.of(address));

		mockMvc.perform(get(BASE_URL + "/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.streetName", is("streetName")));

		verify(addressService, times(1)).findById(1L);
	}

	@Test(expected = Exception.class)
	public void shouldReturnNotFoundWhenAddressIdIsInvalid() throws Exception {
		when(addressService.findById(2L)).thenReturn(Optional.of(address));
		mockMvc.perform(get(BASE_URL + "/1"));
	}

	@Test
	public void shouldUpateAddressSuccessfully() throws JsonProcessingException, Exception {
		AddressForm addressForm = new AddressForm();
		addressForm.setCep(address.getCep());
		addressForm.setDistrict(address.getDistrict());
		addressForm.setNumber(address.getNumber());
		addressForm.setState(address.getState());
		addressForm.setStreetName(address.getStreetName());

		when(addressService.findById(1L)).thenReturn(Optional.of(address));
		when(addressService.save(any(Address.class))).thenReturn(address);

		mockMvc.perform(patch(BASE_URL + "/1")
				.content(objecMapper.writeValueAsString(addressForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted())
		.andExpect(jsonPath("$.id", is(1)));

		verify(addressService, times(1)).save(any(Address.class));
	}

	@Test(expected = Exception.class)
	public void updateAddress_withInvalidAddressId_shouldThrowException()
			throws JsonProcessingException, Exception {
		when(addressService.findById(1L)).thenReturn(Optional.empty());
		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(address))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
	}

	@Test
	public void shouldReturnNotFoundWhenCustomerIsInvalid() throws JsonProcessingException, Exception {
		mockMvc.perform(put(BASE_URL + "/1")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(customerService, never()).findById(eq(customerId));
		verify(addressService, never()).findById(eq(addressId));
	}

	@Test
	public void shouldDeleteAddress() throws Exception {
		address.setId(1L);

		when(addressService.findById(1L)).thenReturn(Optional.of(address));

		mockMvc.perform(delete(BASE_URL + "/1"))
		.andExpect(status().isOk());

		verify(addressService, times(1)).remove(1L);
	}

	@Test(expected = Exception.class)
	public void removeAddress_withInvalidAddressId_shouldThrowException() throws Exception {
		when(addressService.findById(1L)).thenReturn(Optional.empty());

		mockMvc.perform(delete(BASE_URL + "/1"));
		verify(addressService, never()).remove(anyLong());
	}
}
