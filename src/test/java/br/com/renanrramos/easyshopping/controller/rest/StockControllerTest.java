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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.form.StockForm;
import br.com.renanrramos.easyshopping.service.impl.StockService;
import br.com.renanrramos.easyshopping.service.impl.StoreService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StockController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class StockControllerTest {

	private static final String BASE_URL = "/api/stocks";

	@InjectMocks
	private StockController stockController;

	@MockBean
	private StockService stockService;

	@MockBean
	private StoreService storeService;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
	}

	@Test
	public void saveStock_withValidParameters_shouldCreateSuccessfully() throws JsonProcessingException, Exception {

		when(storeService.findById(anyLong())).thenReturn(Optional.of(getStoreInstance(1L)));
		when(stockService.save(any(Stock.class))).thenReturn(getStockInstance(1L));

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStockForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(storeService, times(1)).findById(anyLong());
		verify(stockService, times(1)).save(any(Stock.class));
	}

	@Test(expected = Exception.class)
	public void saveStock_withoutStoreId_shouldThrowException() throws JsonProcessingException, Exception {

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(new StockForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(storeService, never()).findById(anyLong());
		verify(stockService, never()).save(any(Stock.class));
	}

	@Test(expected = Exception.class)
	public void saveStock_whenStoreIdNotFound_shouldThrowException() throws JsonProcessingException, Exception {

		when(storeService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getStockForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(storeService, times(1)).findById(anyLong());
		verify(stockService, never()).save(any(Stock.class));
	}

	@Test
	public void getStocks_whenNameParameterIsNull_shouldReturnAListOfStocks() throws Exception {
		List<Stock> stocks = new ArrayList<>();
		stocks.add(getStockInstance(1L));
		stocks.add(getStockInstance(2L));
		stocks.add(getStockInstance(3L));

		when(stockService.findAll(any(Pageable.class))).thenReturn(stocks);
		mockMvc.perform(get(BASE_URL)).andExpect(status().isOk());

		verify(stockService, times(1)).findAll(any(Pageable.class));
	}

	@Test
	public void getStocks_withValidNameParameter_shouldReturnAListOfStocks() throws Exception {
		List<Stock> stocks = new ArrayList<>();
		stocks.add(getStockInstance(1L));
		stocks.add(getStockInstance(2L));
		stocks.add(getStockInstance(3L));

		when(stockService.findStockByName(any(Pageable.class), anyString())).thenReturn(stocks);
		mockMvc.perform(get(BASE_URL).param("name", "name")).andExpect(status().isOk());

		verify(stockService, times(1)).findStockByName(any(Pageable.class), anyString());
	}

	@Test
	public void getStockById_withValidParameters_shouldReturnStock() throws Exception {
		when(stockService.findById(anyLong())).thenReturn(Optional.of(getStockInstance(1L)));

		mockMvc.perform(get(BASE_URL + "/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("stockName")));

		verify(stockService, times(1)).findById(anyLong());
	}

	@Test(expected = Exception.class)
	public void getStockById_withInvalidStockId_shouldThrowException() throws Exception {
		when(stockService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL + "/1"));

		verify(stockService, times(1)).findById(anyLong());
	}

	@Test
	public void updateStock_withValidParameters_shouldUpdateSuccessfully() throws JsonProcessingException, Exception {

		Stock stock = getStockInstance(1L);

		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));
		when(storeService.findById(anyLong())).thenReturn(Optional.of(getStoreInstance(1L)));
		when(stockService.update(any(Stock.class))).thenReturn(stock);

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStockForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("stockName")));

		verify(stockService, times(1)).findById(anyLong());
		verify(storeService, times(1)).findById(anyLong());
		verify(stockService, times(1)).update(any(Stock.class));
	}

	@Test(expected = Exception.class)
	public void updateStock_whenStockNotFound_shouldThrowException() throws JsonProcessingException, Exception {

		when(stockService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStockForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(stockService, times(1)).findById(anyLong());
		verify(storeService, never()).findById(anyLong());
		verify(stockService, never()).update(any(Stock.class));
	}

	@Test(expected = Exception.class)
	public void updateStock_whenStoreIdIsNull_shouldThrowException() throws JsonProcessingException, Exception {

		Stock stock = getStockInstance(1L);

		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(new StockForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(stockService, times(1)).findById(anyLong());
		verify(storeService, never()).findById(anyLong());
		verify(stockService, never()).update(any(Stock.class));
	}

	@Test(expected = Exception.class)
	public void updateStock_whenStoreNotFound_shouldThrowException() throws JsonProcessingException, Exception {

		Stock stock = getStockInstance(1L);

		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));
		when(storeService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getStockForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(stockService, times(1)).findById(anyLong());
		verify(storeService, times(1)).findById(anyLong());
		verify(stockService, never()).update(any(Stock.class));
	}

	@Test
	public void removeStock_withValidParameters_shouldRemoveStockSuccessfully() throws Exception {
		Stock stock = getStockInstance(1L);

		when(stockService.findById(anyLong())).thenReturn(Optional.of(stock));

		mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());

		verify(stockService, times(1)).findById(anyLong());
		verify(stockService, times(1)).remove(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeStock_whenStockNotFound_shouldThrowException() throws Exception {
		getStockInstance(1L);

		when(stockService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());

		verify(stockService, times(1)).findById(anyLong());
		verify(stockService, never()).remove(anyLong());
	}

	private Store getStoreInstance(Long id) {
		final Store store = new Store();
		store.setId(id);
		store.setName("storeName");
		store.setRegisteredNumber("registeredNumber");
		return store;
	}

	private StockForm getStockForm() {
		final StockForm stockForm = new StockForm();
		stockForm.setName("stockName");
		stockForm.setStoreId(1L);
		return stockForm;
	}

	private Stock getStockInstance(Long id) {
		final Stock stock = new Stock();
		stock.setId(id);
		stock.setName("stockName");
		stock.setStore(getStoreInstance(id));
		return stock;
	}

}
