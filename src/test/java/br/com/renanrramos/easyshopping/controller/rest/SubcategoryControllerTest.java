/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 24/11/2020
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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

import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.model.builder.ProductCategoryBuilder;
import br.com.renanrramos.easyshopping.model.builder.SubcategoryBuilder;
import br.com.renanrramos.easyshopping.model.form.SubcategoryForm;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import br.com.renanrramos.easyshopping.service.impl.SubcategoryService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SubcategoryController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-admin" })
public class SubcategoryControllerTest {

	private final String BASE_URL = "/api/subcategories";

	@InjectMocks
	private SubcategoryController subcategoryController;

	@MockBean
	private SubcategoryService subcategoryService;

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
		mockMvc = MockMvcBuilders.standaloneSetup(subcategoryController).build();
	}

	@Test
	public void saveSubcategory_withValidSubcategory_shouldCreateSuccessfully()
			throws JsonProcessingException, Exception {
		SubcategoryForm subcategoryForm = new SubcategoryForm("subcategory", 1L);
		Subcategory subcategory = getSubcategoryInstance();
		subcategory.setId(1L);
		when(subcategoryService.save(any(Subcategory.class))).thenReturn(subcategory);
		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance()));

		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("subcategory")));

		verify(subcategoryService, times(1)).save(any(Subcategory.class));
		verify(productCategoryService, times(1)).findById(anyLong());
	}

	@Test(expected = Exception.class)
	public void saveSubcategory_withoutProductCategoryId_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubcategoryForm subcategoryForm = new SubcategoryForm();

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subcategoryService, never()).save(any(Subcategory.class));
		verify(productCategoryService, never()).findById(anyLong());
	}

	@Test(expected = Exception.class)
	public void saveSubcategory_whenoutProductCategoryNotFound_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubcategoryForm subcategoryForm = new SubcategoryForm("subcategory", 1L);

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subcategoryService, never()).save(any(Subcategory.class));
		verify(productCategoryService, times(1)).findById(anyLong());
	}

	@Test
	public void getSubcategories_withoutNameAndProductCategoryIdParameter_shouldReturnListOfSubcategories()
			throws Exception {
		List<Subcategory> subcategories = new ArrayList<>();
		subcategories.add(getSubcategoryInstance());
		subcategories.add(getSubcategoryInstance());

		when(subcategoryService.findAllPageable(eq(page), anyLong())).thenReturn(subcategories);
		mockMvc.perform(get(BASE_URL).param("name", "").param("productCategoryId", "1")).andExpect(status().isOk());

		verify(subcategoryService, times(1)).findAllPageable(any(Pageable.class), anyLong());
	}

	@Test
	public void getSubcategories_withNameAndProductCategoryIdParameter_shouldReturnListOfSubcategories()
			throws Exception {
		List<Subcategory> subcategories = new ArrayList<>();
		subcategories.add(getSubcategoryInstance());
		subcategories.add(getSubcategoryInstance());

		when(subcategoryService.findSubcategoryByName(eq(page), anyString())).thenReturn(subcategories);
		mockMvc.perform(get(BASE_URL).param("name", "subcategory").param("productCategoryId", "1"))
		.andExpect(status().isOk());

		verify(subcategoryService, times(1)).findSubcategoryByName(any(Pageable.class), anyString());
	}

	@Test
	public void getSubcategoriesBydId_withValidSubcategoryId_shoulReturnSubcategory() throws Exception {
		Subcategory subcategory = getSubcategoryInstance();
		subcategory.setId(1L);
		when(subcategoryService.findById(anyLong())).thenReturn(Optional.of(subcategory));

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("subcategory")));

		verify(subcategoryService, times(1)).findById(anyLong());
	}

	@Test
	public void getSubcategoriesBydId_withInalidSubcategoryId_shoulReturnNotFound() throws Exception {

		when(subcategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isNotFound());

		verify(subcategoryService, times(1)).findById(anyLong());
	}

	@Test
	public void updateSubcategory_withValidSubcategory_shouldUpdateSuccessfully()
			throws JsonProcessingException, Exception {
		SubcategoryForm subcategoryForm = new SubcategoryForm("subcategory", 1L);
		Subcategory subcategory = getSubcategoryInstance();
		subcategory.setId(1L);

		when(subcategoryService.save(any(Subcategory.class))).thenReturn(subcategory);
		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance()));
		when(subcategoryService.findById(anyLong())).thenReturn(Optional.of(subcategory));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("subcategory")));

		verify(subcategoryService, times(1)).findById(anyLong());
		verify(productCategoryService, times(1)).findById(anyLong());
		verify(subcategoryService, times(1)).save(any(Subcategory.class));
	}

	@Test(expected = Exception.class)
	public void updateSubcategory_withoutInvalidProductCategoryId_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubcategoryForm subcategoryForm = new SubcategoryForm("subcategory", null);
		Subcategory subcategory = getSubcategoryInstance();
		subcategory.setId(1L);

		when(subcategoryService.save(any(Subcategory.class))).thenReturn(subcategory);
		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance()));
		when(subcategoryService.findById(anyLong())).thenReturn(Optional.of(subcategory));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subcategoryService, never()).findById(anyLong());
		verify(productCategoryService, never()).findById(anyLong());
		verify(subcategoryService, never()).save(any(Subcategory.class));
	}

	@Test(expected = Exception.class)
	public void updateSubcategory_withtInvalidProductCategoryId_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubcategoryForm subcategoryForm = new SubcategoryForm("subcategory", 12L);
		Subcategory subcategory = getSubcategoryInstance();
		subcategory.setId(1L);

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subcategoryService, never()).findById(anyLong());
		verify(productCategoryService, times(1)).findById(anyLong());
		verify(subcategoryService, never()).save(any(Subcategory.class));
	}

	@Test(expected = Exception.class)
	public void updateSubcategory_withtInvalidSubcategoryId_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubcategoryForm subcategoryForm = new SubcategoryForm("subcategory", 12L);
		Subcategory subcategory = getSubcategoryInstance();
		subcategory.setId(1L);

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance()));
		when(subcategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subcategoryService, times(1)).findById(anyLong());
		verify(productCategoryService, times(1)).findById(anyLong());
		verify(subcategoryService, never()).save(any(Subcategory.class));
	}

	@Test
	public void removeSubcategory_withValidParameters_shouldRemoveSuccessfully() throws Exception {
		Subcategory subcategory = getSubcategoryInstance();
		subcategory.setId(1L);

		when(subcategoryService.findById(anyLong())).thenReturn(Optional.of(subcategory));
		when(productService.isThereAnyProductWithSubcategoryId(anyLong())).thenReturn(false);

		mockMvc.perform(delete(BASE_URL + "/1"));

		verify(subcategoryService, times(1)).remove(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeSubcategory_withInvalidSubcategoryId_shouldThrowException() throws Exception {

		when(subcategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(delete(BASE_URL + "/1"));

		verify(subcategoryService, never()).remove(anyLong());
		verify(productService, never()).isThereAnyProductWithSubcategoryId(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeSubcategory_whenThereIsProductWithSubcategoryId_shouldThrowException() throws Exception {
		Subcategory subcategory = getSubcategoryInstance();
		subcategory.setId(1L);
		when(subcategoryService.findById(anyLong())).thenReturn(Optional.of(subcategory));
		when(productService.isThereAnyProductWithSubcategoryId(anyLong())).thenReturn(true);

		mockMvc.perform(delete(BASE_URL + "/1"));

		verify(subcategoryService, never()).remove(anyLong());
		verify(productService, times(1)).isThereAnyProductWithSubcategoryId(anyLong());
	}

	private Subcategory getSubcategoryInstance() {
		return SubcategoryBuilder.builder()
				.withName("subcategory")
				.withProductCategory(getProductCategoryInstance()).build();
	}

	private ProductCategory getProductCategoryInstance() {
		return ProductCategoryBuilder.builder().withId(1L).withName("productCategory").build();
	}
}
