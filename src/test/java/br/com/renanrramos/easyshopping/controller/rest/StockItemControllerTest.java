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
import static org.mockito.ArgumentMatchers.eq;
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
import java.util.Collections;
import java.util.Optional;

import org.hamcrest.core.IsNot;
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

import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.form.StockItemForm;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import br.com.renanrramos.easyshopping.service.impl.StockItemService;
import br.com.renanrramos.easyshopping.service.impl.StockService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StockItemController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class StockItemControllerTest {

	private static final String BASE_URL = "/api/stock-items";

	@InjectMocks
	private StockItemController stockItemController;

	@MockBean
	private StockItemService itemService;

	@MockBean
	private ProductService productService;

	@MockBean
	private StockService stockService;

	@Mock
	private Pageable page;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(stockItemController).build();
	}

	@Test
	public void saveStockItem_withValidParameters_shouldCreateSuccessfully() throws JsonProcessingException, Exception {
		Stock stock = EasyShoppingUtil.getStockInstance(1L);
		stock.setStore(EasyShoppingUtil.getStoreInstance(1L));
		StockItem  stockItem = EasyShoppingUtil.getStockItemInstance();
		stockItem.setStock(stock);
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));
		when(itemService.save(any(StockItem.class))).thenReturn(stockItem);

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10)));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, times(1)).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_whenMinAmountIsNull_shouldThrowException() throws JsonProcessingException, Exception {
		Stock stock = EasyShoppingUtil.getStockInstance(1L);
		stock.setStore(EasyShoppingUtil.getStoreInstance(1L));
		StockItem  stockItem = EasyShoppingUtil.getStockItemInstance();
		stockItem.setStock(stock);
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(new StockItemForm(1L, 20.0, null, 10, 1L, "productName")))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10)));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_whenMinAmountIsLessThanZero_shouldThrowException() throws JsonProcessingException, Exception {
		Stock stock = EasyShoppingUtil.getStockInstance(1L);
		stock.setStore(EasyShoppingUtil.getStoreInstance(1L));
		StockItem  stockItem = EasyShoppingUtil.getStockItemInstance();
		stockItem.setStock(stock);
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(new StockItemForm(1L, 20.0, -2.0, 10, 1L, "productName")))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10)));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_whenMinAmountIsGreaterThanMaxAmount_shouldThrowException() throws JsonProcessingException, Exception {
		Stock stock = EasyShoppingUtil.getStockInstance(1L);
		stock.setStore(EasyShoppingUtil.getStoreInstance(1L));
		StockItem  stockItem = EasyShoppingUtil.getStockItemInstance();
		stockItem.setStock(stock);
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(new StockItemForm(1L, 2.0, 25.0, 10, 1L, "productName")))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10)));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_withoutProductId_shouldThrowException() throws JsonProcessingException, Exception {

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(null, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, never()).hasStockItemByProductId(anyLong());
		verify(productService, never()).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_whenProductAlreadyInAStock_shouldThrowException()
			throws JsonProcessingException, Exception {

		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(true);
		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, never()).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_whenProductNotFound_shouldThrowException() throws JsonProcessingException, Exception {

		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_withoutStockId_shouldThrowException() throws JsonProcessingException, Exception {
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, null)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_whenStockNotFound_shouldThrowException() throws JsonProcessingException, Exception {
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test
	public void updateStockItem_withValidParameters_shouldUpdateSuccessfully()
			throws JsonProcessingException, Exception {

		StockItem stockItem = EasyShoppingUtil.getStockItemInstance();
		Store store = EasyShoppingUtil.getStoreInstance(1L);
		Stock stock = EasyShoppingUtil.getStockInstance(1L);

		stock.setStore(store);
		stockItem.setStock(stock);

		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));
		when(itemService.findById(anyLong())).thenReturn(Optional.of(stockItem));
		when(itemService.save(any(StockItem.class))).thenReturn(stockItem);

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10)));

		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, times(1)).findById(anyLong());
		verify(itemService, times(1)).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_withInvalidItemIdParameter_shouldThrowException()
			throws JsonProcessingException, Exception {

		StockItem stockItem = EasyShoppingUtil.getStockItemInstance();
		Store store = EasyShoppingUtil.getStoreInstance(1L);
		Stock stock = EasyShoppingUtil.getStockInstance(1L);

		stock.setStore(store);
		stockItem.setStock(stock);

		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));
		when(itemService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10)));

		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, times(1)).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_withoutProductId_shouldThrowException() throws JsonProcessingException, Exception {

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(null, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, never()).hasStockItemByProductId(anyLong());
		verify(productService, never()).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_whenProductAlreadyInAStock_shouldThrowException()
			throws JsonProcessingException, Exception {

		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(true);
		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, never()).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_whenProductNotFound_shouldThrowException() throws JsonProcessingException, Exception {

		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_withoutStockId_shouldThrowException() throws JsonProcessingException, Exception {
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, null)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_whenStockNotFound_shouldThrowException() throws JsonProcessingException, Exception {
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(EasyShoppingUtil.getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_whenMinAmountIsBiggerThanMaxAmount_shouldThrowException()
			throws JsonProcessingException, Exception {

		StockItem stockItem = EasyShoppingUtil.getStockItemInstance();
		Store store = EasyShoppingUtil.getStoreInstance(1L);
		Stock stock = EasyShoppingUtil.getStockInstance(1L);

		stock.setStore(store);
		stockItem.setStock(stock);

		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(new StockItemForm(1L, 2.0, 25.0, 10, 1L, "productName")))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10.0)));

		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_whenMinAmountIsNull_shouldThrowException()
			throws JsonProcessingException, Exception {

		StockItem stockItem = EasyShoppingUtil.getStockItemInstance();
		Store store = EasyShoppingUtil.getStoreInstance(1L);
		Stock stock = EasyShoppingUtil.getStockInstance(1L);

		stock.setStore(store);
		stockItem.setStock(stock);

		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(new StockItemForm(1L, 2.0, null, 10, 1L, "productName")))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10.0)));

		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_whenMinAmountIsLessThanZero_shouldThrowException()
			throws JsonProcessingException, Exception {

		StockItem stockItem = EasyShoppingUtil.getStockItemInstance();
		Store store = EasyShoppingUtil.getStoreInstance(1L);
		Stock stock = EasyShoppingUtil.getStockInstance(1L);

		stock.setStore(store);
		stockItem.setStock(stock);

		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getProductInstance()));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(new StockItemForm(1L, 2.0, -1.0, 10, 1L, "productName")))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10.0)));

		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test
	public void getStockItemById_withValidStockId_shouldReturnStock() throws Exception {
		StockItem stockItem = EasyShoppingUtil.getStockItemInstance();
		Store store = EasyShoppingUtil.getStoreInstance(1L);
		Stock stock = EasyShoppingUtil.getStockInstance(1L);

		stock.setStore(store);
		stockItem.setStock(stock);
		when(itemService.findById(anyLong())).thenReturn(Optional.of(stockItem));

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10)))
		.andExpect(jsonPath("$.maxAmount", is(20.0)))
		.andExpect(jsonPath("$.minAmount", is(5.0)));

		verify(itemService, times(1)).findById(anyLong());
	}

	@Test(expected = Exception.class)
	public void getStockItemById_withInvalidStockId_shouldThrowException() throws Exception {

		when(itemService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isBadRequest());

		verify(itemService, never()).findById(anyLong());
	}

	@Test
	public void getStockItems_withValidParamters_shouldReturnListOfStockItems() throws Exception {
		
		when(stockService.findById(anyLong())).thenReturn(Optional.of(EasyShoppingUtil.getStockInstance(1L)));
		when(itemService.findStockItemByStockId(eq(page), anyLong(), anyString())).thenReturn(Collections.singletonList(EasyShoppingUtil.getStockItemInstance()));

		mockMvc.perform(get(BASE_URL).param("stockId", "1").param("name", "")).andExpect(status().isOk());

		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, times(1)).findStockItemByStockId(any(Pageable.class), anyLong(), anyString());
	}

	@Test(expected = Exception.class)
	public void getStockItems_whenStockNotFound_shouldReturnListOfStockItems() throws Exception {
		
		when(stockService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL));

		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).findStockItemByStockId(any(Pageable.class), anyLong(), anyString());
	}

}
