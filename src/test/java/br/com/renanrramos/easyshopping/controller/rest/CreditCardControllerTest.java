/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
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

import br.com.renanrramos.easyshopping.infra.controller.rest.CreditCardController;
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

import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.CreditCardForm;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.CreditCardService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CreditCardController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class CreditCardControllerTest {

	private final String BASE_URL = "/api/credit-cards";

	@InjectMocks
	private CreditCardController creditCardController;

	@MockBean
	private CreditCardService creditCardService;

	@MockBean
	private AuthenticationServiceImpl mockAuthentication;

	@Mock
	private Pageable page;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	private CreditCard creditCard;

	private CreditCardForm creditCardForm;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(creditCardController).build();

		when(mockAuthentication.getName()).thenReturn("customerId");
		creditCard = EasyShoppingUtil.getCreditCardInstance(1L);
		creditCardForm = EasyShoppingUtil.getCreditCardForm();
	}

	@Test
	public void saveCreditCard_withValidCreditCard_shouldSaveSuccessfully() throws JsonProcessingException, Exception {
		when(creditCardService.save(any(CreditCard.class))).thenReturn(creditCard);
		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(creditCardForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.ownerName", is("OWNER NAME")))
		.andExpect(jsonPath("$.creditCardNumber", is("1234 1234 1234 1234")));

		verify(creditCardService, times(1)).save(any(CreditCard.class));
	}

	@Test
	public void getCreditCards_withValidParameters_shouldReturnAListOFCreditCards() throws Exception {
		List<CreditCard> creditCards = new ArrayList<>();
		creditCards.add(creditCard);
		creditCards.add(creditCard);
		creditCards.add(creditCard);

		when(creditCardService.findAllPageable(any(Pageable.class), anyString())).thenReturn(creditCards);

		mockMvc.perform(get(BASE_URL)).andExpect(status().isOk());

		verify(creditCardService, times(1)).findAllPageable(any(Pageable.class), anyString());
	}

	@Test
	public void getCreditCardById_withValidCreditCardId_shouldReturnCreditCard() throws Exception {
		when(creditCardService.findById(anyLong())).thenReturn(Optional.of(creditCard));

		mockMvc.perform(get(BASE_URL + "/" + creditCard.getId())).andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.ownerName", is("OWNER NAME")));

		verify(creditCardService, times(1)).findById(anyLong());
	}

	@Test(expected = Exception.class)
	public void getCreditCardById_withInvalidCreditCardId_shouldThrowExceptio() throws Exception {
		when(creditCardService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL + "/" + creditCard.getId())).andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.ownerName", is("OWNER NAME")));

		verify(creditCardService, never()).findById(anyLong());
	}

	@Test
	public void updateCreditCard_withValidParameters_shouldUpdatesuccessfully()
			throws JsonProcessingException, Exception {
		when(creditCardService.findById(anyLong())).thenReturn(Optional.of(creditCard));
		when(creditCardService.save(any(CreditCard.class))).thenReturn(creditCard);

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(creditCardForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.ownerName", is("OWNER NAME")))
		.andExpect(jsonPath("$.creditCardNumber", is("1234 1234 1234 1234")));

		verify(creditCardService, times(1)).save(any(CreditCard.class));
	}

	@Test(expected = Exception.class)
	public void updateCreditCard_withInvalidCreditCardId_shouldThrowException()
			throws JsonProcessingException, Exception {

		when(creditCardService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(creditCardForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(creditCardService, times(1)).save(any(CreditCard.class));
	}

	@Test
	public void removeCreditCard_withValidCreditCardId_shouldRemoveSuccessfully() throws Exception {

		when(creditCardService.findById(anyLong())).thenReturn(Optional.of(creditCard));

		mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());

		verify(creditCardService, times(1)).remove(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeCreditCard_withInvalidCreditCardId_shouldThrowException() throws Exception {

		when(creditCardService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(delete(BASE_URL + "/1"));

		verify(creditCardService, never()).remove(anyLong());
	}
}
