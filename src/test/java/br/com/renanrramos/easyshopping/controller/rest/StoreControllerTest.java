/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 25/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.renanrramos.easyshopping.infra.controller.rest.StoreController;
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

import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.StoreForm;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.StoreService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StoreController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class StoreControllerTest {

	private final String BASE_URL = "/api/stores";

	@InjectMocks
	private StoreController storeController;

	@MockBean
	private StoreService storeService;

	@MockBean
	private AuthenticationServiceImpl authenticationServiceImpl;

	@Mock
	private Pageable page;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();
	}

	@Test
	public void saveStore_withValidProperties_shouldCreateSuccessfully() throws JsonProcessingException, Exception {

		when(storeService.isRegisteredNumberInvalid(anyString())).thenReturn(false);
		when(authenticationServiceImpl.getName()).thenReturn("tokenId");
		when(storeService.save(any(Store.class))).thenReturn(EasyShoppingUtil.getStoreInstance(1L));

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStoreForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(storeService, times(1)).isRegisteredNumberInvalid(anyString());
		verify(storeService, times(1)).save(any(Store.class));
	}

	@Test(expected = Exception.class)
	public void saveStore_withInvalidRegisteredNumber_shouldThrowException() throws JsonProcessingException, Exception {

		when(storeService.isRegisteredNumberInvalid(anyString())).thenReturn(true);

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStoreForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(storeService, times(1)).isRegisteredNumberInvalid(anyString());
		verify(authenticationServiceImpl, never()).getName();
		verify(storeService, never()).save(any(Store.class));
	}

	@Test
	public void getStores_withValidParameters_shouldReturnListOfStores() throws Exception {
		List<Store> stores = new ArrayList<>();
		stores.add(EasyShoppingUtil.getStoreInstance(1L));
		stores.add(EasyShoppingUtil.getStoreInstance(3L));
		stores.add(EasyShoppingUtil.getStoreInstance(2L));

		when(storeService.findAll(any(Pageable.class))).thenReturn(stores);

		mockMvc.perform(get(BASE_URL)).andExpect(status().isOk());

		verify(storeService, times(1)).findAll(any(Pageable.class));
	}
	
	@Test
	public void getStores_withEmptyNameParameter_shouldReturnListOfStores() throws Exception {
		List<Store> stores = new ArrayList<>();
		stores.add(EasyShoppingUtil.getStoreInstance(1L));
		stores.add(EasyShoppingUtil.getStoreInstance(3L));
		stores.add(EasyShoppingUtil.getStoreInstance(2L));

		when(storeService.findAll(any(Pageable.class))).thenReturn(stores);

		mockMvc.perform(get(BASE_URL).param("name", ""))
			.andExpect(status().isOk());

		verify(storeService, times(1)).findAll(any(Pageable.class));
	}

	@Test
	public void getStores_withNameParameter_shouldReturnListOfStores() throws Exception {
		List<Store> stores = new ArrayList<>();
		stores.add(EasyShoppingUtil.getStoreInstance(1L));
		stores.add(EasyShoppingUtil.getStoreInstance(3L));
		stores.add(EasyShoppingUtil.getStoreInstance(2L));

		when(storeService.findStoreByName(any(Pageable.class), anyString())).thenReturn(stores);

		mockMvc.perform(get(BASE_URL).param("name", "name")).andExpect(status().isOk());

		verify(storeService, times(1)).findStoreByName(any(Pageable.class), anyString());
	}

	@Test
	public void getCompanyOwnStores_withNameParameter_shouldReturnListOfStores() throws Exception {
		List<Store> stores = new ArrayList<>();
		stores.add(EasyShoppingUtil.getStoreInstance(1L));
		stores.add(EasyShoppingUtil.getStoreInstance(3L));
		stores.add(EasyShoppingUtil.getStoreInstance(2L));

		when(storeService.findAllPageable(any(Pageable.class), anyString(), anyString())).thenReturn(stores);
		when(authenticationServiceImpl.getName()).thenReturn("tokenId");

		mockMvc.perform(get(BASE_URL + "/company").param("name", "name")).andExpect(status().isOk());

		verify(storeService, times(1)).findAllPageable(any(Pageable.class), anyString(), anyString());
	}

	@Test
	public void getStoreById_withValidStoreId_shouldReturnListOfStores() throws Exception {

		when(storeService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getStoreInstance(1L)));

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("name"))).andExpect(jsonPath("$.corporateName", is("corporateName")))
		.andExpect(jsonPath("$.registeredNumber", is("registeredNumber")));

		verify(storeService, times(1)).findById(anyLong());
	}

	@Test
	public void getStoreById_withInvalidStoreId_shouldReturnNotFound() throws Exception {

		when(storeService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isNotFound());

		verify(storeService, times(1)).findById(anyLong());
	}

	@Test
	public void updateStore_withValidParameters_shouldUpdateSuccessfully() throws JsonProcessingException, Exception {
		when(storeService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getStoreInstance(1L)));
		when(authenticationServiceImpl.getName()).thenReturn("tokenId");
		when(storeService.update(any(Store.class))).thenReturn(EasyShoppingUtil.getStoreInstance(1L));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStoreForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("name")))
		.andExpect(jsonPath("$.corporateName", is("corporateName")))
		.andExpect(jsonPath("$.registeredNumber", is("registeredNumber")));

		verify(storeService, times(1)).update(any(Store.class));
	}

	@Test(expected = Exception.class)
	public void updateStore_whenStoreIdNotFound_shouldThrowExcetion() throws JsonProcessingException, Exception {

		when(storeService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStoreForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(storeService, never()).update(any(Store.class));
	}

	@Test
	public void removeStore_withValidStoreId_shouldRemoveSuccessfully() throws Exception {
		when(storeService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getStoreInstance(1L)));

		mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());
		verify(storeService, times(1)).findById(anyLong());
		verify(storeService, times(1)).remove(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeStore_whenStoreIdNotFound_shouldThrowException() throws Exception {
		when(storeService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(delete(BASE_URL + "/1"));
		verify(storeService, times(1)).findById(anyLong());
		verify(storeService, never()).remove(anyLong());
	}

	private StoreForm getStoreForm() {
		StoreForm storeForm = new StoreForm();
		storeForm.setCompanyId("companyId");
		storeForm.setCorporateName("corporateName");
		storeForm.setName("name");
		storeForm.setRegisteredNumber("registeredNumber");
		return storeForm;
	}
}
