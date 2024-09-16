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

import br.com.renanrramos.easyshopping.infra.controller.rest.ProductCategoryController;
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

import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.ProductCategoryForm;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProductCategoryController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-user" })
public class ProductCategoryControllerTest {

	private final String BASE_URL = "/api/product-categories";

	@InjectMocks
	private ProductCategoryController productCategoryController;

	@MockBean
	private ProductCategoryService productCategoryService;

	@MockBean
	private ProductService productService;

	@MockBean
	private AuthenticationServiceImpl mockAuthentication;

	@Mock
	private Pageable page;

	private MockMvc mockMvc;

	private ObjectMapper objecMapper = new ObjectMapper();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productCategoryController).build();

		when(mockAuthentication.getName()).thenReturn("customerId");
	}

	@Test
	public void saveProductCategory_withValidProperties_shouldCreateSuccessfully()
			throws JsonProcessingException, Exception {

		when(productCategoryService.save(any(ProductCategory.class))).thenReturn(getProductCategoryInstance(1L));

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getProductCategoryForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("name")));

		verify(productCategoryService, times(1)).save(any(ProductCategory.class));
	}

	@Test(expected = Exception.class)
	public void saveProductCategory_whenOccursInternalServerError_shouldThrowException()
			throws JsonProcessingException, Exception {

		when(productCategoryService.save(any(ProductCategory.class))).thenReturn(new ProductCategory());

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(getProductCategoryForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(productCategoryService, never()).save(any(ProductCategory.class));
	}

	@Test
	public void getProductCategories_withEmptyNameParameter_shouldReturnListOfProductCategories() throws Exception {
		List<ProductCategory> productCategories = new ArrayList<>();
		productCategories.add(getProductCategoryInstance(1L));
		productCategories.add(getProductCategoryInstance(2L));
		productCategories.add(getProductCategoryInstance(3L));

		when(productCategoryService.findAllPageable(any(Pageable.class), anyLong())).thenReturn(productCategories);

		mockMvc.perform(get(BASE_URL).param("name", "")).andExpect(status().isOk());

		verify(productCategoryService, times(1)).findAllPageable(any(Pageable.class), any());
	}

	@Test
	public void getProductCategories_withNullNameParameter_shouldReturnListOfProductCategories() throws Exception {
		List<ProductCategory> productCategories = new ArrayList<>();
		productCategories.add(getProductCategoryInstance(1L));
		productCategories.add(getProductCategoryInstance(2L));
		productCategories.add(getProductCategoryInstance(3L));

		when(productCategoryService.findAllPageable(any(Pageable.class), anyLong())).thenReturn(productCategories);

		mockMvc.perform(get(BASE_URL)).andExpect(status().isOk());

		verify(productCategoryService, times(1)).findAllPageable(any(Pageable.class), any());
	}

	@Test
	public void getProductCategories_withNameParameter_shouldReturnListOfProductCategories() throws Exception {
		List<ProductCategory> productCategories = new ArrayList<>();
		productCategories.add(getProductCategoryInstance(1L));
		productCategories.add(getProductCategoryInstance(2L));
		productCategories.add(getProductCategoryInstance(3L));

		when(productCategoryService.findAllProductCategoriesByName(any(Pageable.class), anyString()))
		.thenReturn(productCategories);

		mockMvc.perform(get(BASE_URL).param("name", "name")).andExpect(status().isOk());

		verify(productCategoryService, times(1)).findAllProductCategoriesByName(any(Pageable.class), anyString());
	}

	@Test
	public void getProductCategoryById_withValidProductCategoryId_shouldReturnProductCategory() throws Exception {
		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance(1L)));

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("name")));

		verify(productCategoryService, times(1)).findById(anyLong());
	}

	@Test
	public void getProductCategoryById_withInvalidProductCategoryId_shouldReturnNotFound() throws Exception {
		when(productCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isNotFound());

		verify(productCategoryService, times(1)).findById(anyLong());
	}

	@Test
	public void updateProductCategory_withValidProperties_shouldUpdateSuccessfully()
			throws JsonProcessingException, Exception {

		when(productCategoryService.update(any(ProductCategory.class))).thenReturn(getProductCategoryInstance(1L));
		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance(1L)));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getProductCategoryForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("name")));

		verify(productCategoryService, times(1)).update(any(ProductCategory.class));
	}

	@Test(expected = Exception.class)
	public void updateProductCategory_withInvalidProductCategoryId_shouldThrowException()
			throws JsonProcessingException, Exception {

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(getProductCategoryForm()))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(productCategoryService, never()).update(any(ProductCategory.class));
	}

	@Test
	public void removeProductCategory_withValidProductCategoryId_shouldRemoveSuccessfully() throws Exception {

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance(1L)));
		when(productService.isThereAnyProductWithSubCategoryId(anyLong())).thenReturn(false);

		mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());

		verify(productCategoryService, times(1)).remove(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeProductCategory_withInvalidProductCategoryId_shouldRemoveSuccessfully() throws Exception {

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());

		verify(productCategoryService, never()).remove(anyLong());
		verify(productService, never()).isThereAnyProductWithSubCategoryId(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeProductCategory_whenProductHasProductCategoryId_shouldThrowException() throws Exception {

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance(1L)));
		when(productService.isThereAnyProductWithSubCategoryId(anyLong())).thenReturn(true);

		mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());

		verify(productCategoryService, never()).remove(anyLong());
		verify(productService, times(1)).isThereAnyProductWithSubCategoryId(anyLong());
	}

	private ProductCategory getProductCategoryInstance(Long id) {
		final ProductCategory productCategory = new ProductCategory();
		productCategory.setId(id);
		productCategory.setName("name");
		return productCategory;
	}

	private ProductCategoryForm getProductCategoryForm() {
		ProductCategoryForm productCategoryForm = new ProductCategoryForm();
		productCategoryForm.setName("name");
		return productCategoryForm;
	}
}
