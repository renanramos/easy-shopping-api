/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
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

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.model.Purchase;
import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.builder.StockItemBuilder;
import br.com.renanrramos.easyshopping.model.form.PurchaseForm;
import br.com.renanrramos.easyshopping.service.impl.AddressService;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.CreditCardService;
import br.com.renanrramos.easyshopping.service.impl.OrderItemService;
import br.com.renanrramos.easyshopping.service.impl.OrderService;
import br.com.renanrramos.easyshopping.service.impl.PurchaseService;
import br.com.renanrramos.easyshopping.service.impl.StockItemService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PurchaseController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-admin", "easy-shopping-user" })
public class PurchaseControllerTest {

	private static final String BASE_URL = "/api/purchases";

	@InjectMocks
	private PurchaseController purchaseController;

	@MockBean
	private PurchaseService purchaseService;

	@MockBean
	private OrderService orderService;

	@MockBean
	private AddressService addressService;

	@MockBean
	private CreditCardService creditCardService;

	@MockBean
	private OrderItemService orderItemService;

	@MockBean
	private StockItemService stockItemService;

	@MockBean
	private AuthenticationServiceImpl authenticationServiceImpl;

	@Mock
	private Pageable page;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	private Purchase purchase;

	private Order order;

	private Address address;

	private CreditCard creditCard;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();
		purchase = EasyShoppingUtil.getPurchaseInstance(1L);
		order = EasyShoppingUtil.getOrderInstance();
		address = EasyShoppingUtil.getAddressInstance();
		creditCard = EasyShoppingUtil.getCreditCardInstance(1L);
	}

	@Test(expected = Exception.class)
	public void savePurchase_withNullOrderId_shouldThrowException() throws JsonProcessingException, Exception {
		mockMvc.perform(
				post(BASE_URL).content(objecMapper.writeValueAsString(new PurchaseForm("customerId", null, 1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
		verify(purchaseService, never()).save(any(Purchase.class));
	}

	@Test (expected = Exception.class)
	public void savePurchase_whenOrderNotFound_shouldThrowException() throws JsonProcessingException, Exception {
		when(orderService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(new PurchaseForm("customerId", 1L, 1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(addressService, never()).findById(anyLong());
		verify(creditCardService, never()).findById(anyLong());
		verify(purchaseService, never()).save(any(Purchase.class));
	}

	@Test(expected = Exception.class)
	public void savePurchase_withNullAddressId_shouldThrowException() throws JsonProcessingException, Exception {
		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));

		mockMvc.perform(
				post(BASE_URL).content(objecMapper.writeValueAsString(new PurchaseForm("customerId", 1L, null, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(addressService, never()).findById(anyLong());
		verify(creditCardService, never()).findById(anyLong());
		verify(purchaseService, never()).save(any(Purchase.class));
	}

	@Test(expected = Exception.class)
	public void savePurchase_whenAddressNotFound_shouldThrowException() throws JsonProcessingException, Exception {
		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
		when(addressService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(
				post(BASE_URL).content(objecMapper.writeValueAsString(new PurchaseForm("customerId", 1L, 1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(addressService, times(1)).findById(anyLong());
		verify(creditCardService, never()).findById(anyLong());
		verify(purchaseService, never()).save(any(Purchase.class));
	}

	@Test(expected = Exception.class)
	public void savePurchase_withNullCreditCardId_shouldThrowException() throws JsonProcessingException, Exception {
		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
		when(addressService.findById(anyLong())).thenReturn(Optional.of(address));

		mockMvc.perform(
				post(BASE_URL).content(objecMapper.writeValueAsString(new PurchaseForm("customerId", 1L, 1L, null)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(addressService, times(1)).findById(anyLong());
		verify(creditCardService, never()).findById(anyLong());
		verify(purchaseService, never()).save(any(Purchase.class));
	}

	@Test(expected = Exception.class)
	public void savePurchase_whenCreditCardNotFound_shouldThrowException() throws JsonProcessingException, Exception {
		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
		when(addressService.findById(anyLong())).thenReturn(Optional.of(address));
		when(creditCardService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(
				post(BASE_URL).content(objecMapper.writeValueAsString(new PurchaseForm("customerId", 1L, 1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(addressService, times(1)).findById(anyLong());
		verify(creditCardService, times(1)).findById(anyLong());
		verify(purchaseService, never()).save(any(Purchase.class));
	}

	@Test
	public void savePurchase_withValidParameters_shouldCreateSuccessfully() throws JsonProcessingException, Exception {
		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
		when(addressService.findById(anyLong())).thenReturn(Optional.of(address));
		when(creditCardService.findById(anyLong())).thenReturn(Optional.of(creditCard));
		when(purchaseService.save(any(Purchase.class))).thenReturn(purchase);

		mockMvc.perform(
				post(BASE_URL).content(objecMapper.writeValueAsString(new PurchaseForm("customerId", 1L, 1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.customerId", is("customerId")));

		verify(orderService, times(1)).findById(anyLong());
		verify(addressService, times(1)).findById(anyLong());
		verify(creditCardService, times(1)).findById(anyLong());
		verify(purchaseService, times(1)).save(any(Purchase.class));
	}

	@Test
	public void savePurchase_withOrderItems_shouldCreateSuccessfully() throws JsonProcessingException, Exception {
		OrderItem orderItem = EasyShoppingUtil.getOrderItemInstance();
		orderItem.setId(1L);
		
		order.setItems(Arrays.asList(orderItem));

		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
		when(addressService.findById(anyLong())).thenReturn(Optional.of(address));
		when(creditCardService.findById(anyLong())).thenReturn(Optional.of(creditCard));
		when(purchaseService.save(any(Purchase.class))).thenReturn(purchase);
		when(stockItemService.findStockItemByProductId(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getStockItemInstance()));
		when(stockItemService.update(any(StockItem.class))).thenReturn(EasyShoppingUtil.getStockItemInstance());

		mockMvc.perform(
				post(BASE_URL).content(objecMapper.writeValueAsString(new PurchaseForm("customerId", 1L, 1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.customerId", is("customerId")));

		verify(orderService, times(1)).findById(anyLong());
		verify(addressService, times(1)).findById(anyLong());
		verify(creditCardService, times(1)).findById(anyLong());
		verify(purchaseService, times(1)).save(any(Purchase.class));
	}

	@Test
	public void savePurchase_whenOrderItemWithNullIdValue_shouldCreateSuccessfully() throws JsonProcessingException, Exception {
		OrderItem orderItem = EasyShoppingUtil.getOrderItemInstance();
		orderItem.setId(1L);

		order.setItems(Arrays.asList(orderItem));

		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
		when(addressService.findById(anyLong())).thenReturn(Optional.of(address));
		when(creditCardService.findById(anyLong())).thenReturn(Optional.of(creditCard));
		when(purchaseService.save(any(Purchase.class))).thenReturn(purchase);
		when(stockItemService.findStockItemByProductId(anyLong())).thenReturn(Optional.of(StockItemBuilder.builder().withId(null).build()));
		when(stockItemService.update(any(StockItem.class))).thenReturn(EasyShoppingUtil.getStockItemInstance());

		mockMvc.perform(
				post(BASE_URL).content(objecMapper.writeValueAsString(new PurchaseForm("customerId", 1L, 1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.customerId", is("customerId")));

		verify(orderService, times(1)).findById(anyLong());
		verify(addressService, times(1)).findById(anyLong());
		verify(creditCardService, times(1)).findById(anyLong());
		verify(purchaseService, times(1)).save(any(Purchase.class));
	}

	@Test
	public void orderItemStatistic() throws Exception {
		when(orderItemService.orderItemStatistic()).thenReturn(Arrays.asList(EasyShoppingUtil.getOrderItemInstance()));

		mockMvc.perform(get(BASE_URL + "/statistics"));

		verify(orderItemService, times(1)).orderItemStatistic();
	}
}
