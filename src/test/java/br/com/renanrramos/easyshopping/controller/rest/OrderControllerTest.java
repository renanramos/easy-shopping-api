/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 27/11/2020
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.OrderService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OrderController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class OrderControllerTest {

	private static final String BASE_URL = "/api/orders";

	@InjectMocks
	private OrderController orderController;

	@MockBean
	private OrderService orderService;

	@MockBean
	private AuthenticationServiceImpl authenticationServiceImpl;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	private Order order;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
		order = EasyShoppingUtil.getOrderInstance();
	}

	@Test
	public void saveOrder_withValidParameters_shouldCreateSuccessfully() throws JsonProcessingException, Exception {

		when(orderService.save(any(Order.class))).thenReturn(order);
		when(authenticationServiceImpl.getName()).thenReturn("customerId");

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(order))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated()).andExpect(jsonPath("$.customerId", is("customerId")))
		.andExpect(jsonPath("$.orderNumber", is("orderNumber")));
		verify(orderService, times(1)).save(any(Order.class));
	}

	@Test
	public void updateOrder_withValidParameters_shouldUpdateSuccessfully() throws JsonProcessingException, Exception {

		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
		when(orderService.update(any(Order.class))).thenReturn(order);

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(order))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.customerId", is("customerId")))
		.andExpect(jsonPath("$.orderNumber", is("orderNumber")));

		verify(orderService, times(1)).findById(anyLong());
		verify(orderService, times(1)).update(any(Order.class));
	}

	@Test(expected = Exception.class)
	public void updateOrder_whenOrderNotFound_shouldThrowException() throws JsonProcessingException, Exception {

		when(orderService.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(order))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(orderService, never()).update(any(Order.class));
	}

	@Test
	public void getOrders_withCustomerTokenId_shouldReturnListOfOrdersSuccessfully() throws Exception {
		
		when(authenticationServiceImpl.getName()).thenReturn("customerTokenId");
		when(orderService.findCustomerOrders(anyString()))
			.thenReturn(Arrays.asList(EasyShoppingUtil.getOrderInstance()));

		mockMvc.perform(get(BASE_URL))
		.andExpect(status().isOk());

		verify(authenticationServiceImpl, times(1)).getName();
		verify(orderService, times(1)).findCustomerOrders(anyString());
	}
}
