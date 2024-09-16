/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 26/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.Optional;

import br.com.renanrramos.easyshopping.infra.controller.rest.OrderItemController;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.OrderItemMapper;
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
import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.OrderItemForm;
import br.com.renanrramos.easyshopping.service.impl.OrderItemService;
import br.com.renanrramos.easyshopping.service.impl.OrderService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OrderItemController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class OrderItemControllerTest {

	private static final String BASE_URL = "/api/order-items";

	@InjectMocks
	private OrderItemController orderItemController;

	@MockBean
	private OrderItemService orderItemService;

	@MockBean
	private OrderService orderService;

	@MockBean
	private ProductService productService;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	private OrderItemForm orderItemForm;

	private Order order;

	private Product product;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(orderItemController).build();
		orderItemForm = EasyShoppingUtil.getOrderItemFormInstance();
		order = EasyShoppingUtil.getOrderInstance();
		product = EasyShoppingUtil.getProductInstance();
	}

	@Test
	public void saveOrderItem_withValidParameters_shouldCreateSuccessfully() throws JsonProcessingException, Exception {

		OrderItem orderItemResponse = OrderItemMapper.INSTANCE.mapOrderItemFormToOrderItem(orderItemForm);
		orderItemResponse.setId(1L);
		order.setId(1L);
		orderItemResponse.setOrder(order);

		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
		when(productService.findById(anyLong())).thenReturn(Optional.of(product));
		when(orderItemService.save(any(OrderItem.class))).thenReturn(orderItemResponse);

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(orderItemForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(orderItemService, times(1)).save(any(OrderItem.class));
	}

	@Test(expected = Exception.class)
	public void saveOrderItem_withoutOrderId_shouldThrowException() throws JsonProcessingException, Exception {

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(new OrderItemForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, never()).findById(anyLong());
		verify(orderItemService, never()).save(any(OrderItem.class));
	}

	@Test(expected = Exception.class)
	public void saveOrderItem_whenOrderNotFound_shouldThrowException() throws JsonProcessingException, Exception {

		when(orderService.findById(anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(orderItemForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(orderItemService, never()).save(any(OrderItem.class));
	}

	@Test(expected = Exception.class)
	public void saveOrderItem_withoutProductId_shouldThrowException() throws JsonProcessingException, Exception {
		orderItemForm.setProductId(null);

		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(orderItemForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(orderItemService, never()).save(any(OrderItem.class));
	}

	@Test(expected = Exception.class)
	public void saveOrderItem_whenProductNotFound_shouldThrowException() throws JsonProcessingException, Exception {

		when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
		when(productService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(orderItemForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(orderService, times(1)).findById(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(orderItemService, never()).save(any(OrderItem.class));
	}

	@Test
	public void getOrderItemsByOrderId_withOrderId_shouldReturnOrderItem() throws Exception {
		when(orderItemService.findOrderItemByOrderId(anyLong()))
			.thenReturn(Arrays.asList(EasyShoppingUtil.getOrderItemInstance()));

		mockMvc.perform(get(BASE_URL + "/1"))
			.andExpect(status().isOk());

		verify(orderItemService, times(1)).findOrderItemByOrderId(anyLong());
		
	}
}
