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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.builder.ProductBuilder;
import br.com.renanrramos.easyshopping.model.builder.StockBuilder;
import br.com.renanrramos.easyshopping.model.builder.StockItemBuilder;
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

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(stockItemController).build();
	}

	@Test
	public void saveStockItem_withValidParameters_shouldCreateSuccessfully() throws JsonProcessingException, Exception {
		StockItem  stockItem = getStockItemInstance();
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(getProductInstance(1L)));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(getStockInstance(1L)));
		when(itemService.save(any(StockItem.class))).thenReturn(stockItem);

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10.0)));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, times(1)).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_withoutProductId_shouldThrowException() throws JsonProcessingException, Exception {

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStockItemForm(null, 1L)))
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
		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStockItemForm(1L, 1L)))
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

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_withoutStockId_shouldThrowException() throws JsonProcessingException, Exception {
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(getProductInstance(1L)));

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStockItemForm(1L, null)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void saveStockItem_whenStockNotFound_shouldThrowException() throws JsonProcessingException, Exception {
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(getProductInstance(1L)));
		when(stockService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test
	public void updateStockItem_withValidParameters_shouldCreateSuccessfully()
			throws JsonProcessingException, Exception {
		StockItem stockItem = getStockItemInstance();
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(getProductInstance(1L)));
		when(stockService.findById(anyLong())).thenReturn(Optional.of(getStockInstance(1L)));
		when(itemService.findById(anyLong())).thenReturn(Optional.of(stockItem));
		when(itemService.save(any(StockItem.class))).thenReturn(stockItem);

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.productName", is("productName")))
		.andExpect(jsonPath("$.currentAmount", is(10.0)));

		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, times(1)).findById(anyLong());
		verify(itemService, times(1)).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_withoutProductId_shouldThrowException() throws JsonProcessingException, Exception {

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStockItemForm(null, 1L)))
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
		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStockItemForm(1L, 1L)))
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

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_withoutStockId_shouldThrowException() throws JsonProcessingException, Exception {
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(getProductInstance(1L)));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStockItemForm(1L, null)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, never()).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	@Test(expected = Exception.class)
	public void updateStockItem_whenStockNotFound_shouldThrowException() throws JsonProcessingException, Exception {
		when(itemService.hasStockItemByProductId(anyLong())).thenReturn(false);
		when(productService.findById(anyLong())).thenReturn(Optional.of(getProductInstance(1L)));
		when(stockService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStockItemForm(1L, 1L)))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(itemService, times(1)).hasStockItemByProductId(anyLong());
		verify(productService, times(1)).findById(anyLong());
		verify(stockService, times(1)).findById(anyLong());
		verify(itemService, never()).save(any(StockItem.class));
	}

	private Stock getStockInstance(Long id) {
		return StockBuilder.builder().withId(id).withName("stockName").build();
	}

	private Product getProductInstance(Long id) {
		return ProductBuilder.builder().withId(id).withName("productName").withCompany("companyId").withPrice(20.00)
				.build();
	}

	private StockItemForm getStockItemForm(Long productId, Long stockId) {
		return new StockItemForm(productId, 20.0, 5.0, 10.0, stockId, "productName");
	}

	private StockItem getStockItemInstance() {
		return StockItemBuilder.builder().withId(1L).withCurrentAmount(10.0).withMaxAmount(20.0).withMinAmount(5.0)
				.withProductName("productName").build();
	}

}
