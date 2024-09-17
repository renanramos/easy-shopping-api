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

import br.com.renanrramos.easyshopping.infra.controller.rest.SubCategoryController;
import br.com.renanrramos.easyshopping.model.SubCategory;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.SubCategoryForm;
import br.com.renanrramos.easyshopping.service.impl.SubCategoryService;
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
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;

/**
 * @author renan.ramos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SubCategoryController.class)
@WebAppConfiguration
@WithMockUser(roles = { "easy-shopping-admin" })
public class SubCategoryControllerTest {

	private final String BASE_URL = "/api/subcategories";

	@InjectMocks
	private SubCategoryController subCategoryController;

	@MockBean
	private SubCategoryService subCategoryService;

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
		mockMvc = MockMvcBuilders.standaloneSetup(subCategoryController).build();
	}

	@Test
	public void saveSubCategory_withValidSubcategory_shouldCreateSuccessfully()
			throws JsonProcessingException, Exception {
		SubCategoryForm subCategoryForm = getSubCategoryForm(1L);
		SubCategory subcategory = getSubCategoryInstance();
		subcategory.setId(1L);
		when(subCategoryService.save(any(SubCategory.class))).thenReturn(subcategory);
		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance()));

		mockMvc.perform(post(BASE_URL)
				.content(objecMapper.writeValueAsString(subCategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("subCategory")));

		verify(subCategoryService, times(1)).save(any(SubCategory.class));
		verify(productCategoryService, times(1)).findById(anyLong());
	}

	@Test(expected = Exception.class)
	public void saveSubCategory_withoutProductCategoryId_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubCategoryForm subcategoryForm = new SubCategoryForm();

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subCategoryService, never()).save(any(SubCategory.class));
		verify(productCategoryService, never()).findById(anyLong());
	}

	@Test(expected = Exception.class)
	public void saveSubCategory_whenoutProductCategoryNotFound_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubCategoryForm subcategoryForm = getSubCategoryForm(1L);

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(post(BASE_URL).content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subCategoryService, never()).save(any(SubCategory.class));
		verify(productCategoryService, times(1)).findById(anyLong());
	}

	@Test
	public void getSubCategories_withoutNameAndProductCategoryIdParameter_shouldReturnListOfSubCategories()
			throws Exception {
		List<SubCategory> subCategories = new ArrayList<>();
		subCategories.add(getSubCategoryInstance());
		subCategories.add(getSubCategoryInstance());

		when(subCategoryService.findAllPageable(eq(page), anyLong())).thenReturn(subCategories);
		mockMvc.perform(get(BASE_URL).param("name", "").param("productCategoryId", "1")).andExpect(status().isOk());

		verify(subCategoryService, times(1)).findAllPageable(any(Pageable.class), anyLong());
	}

	@Test
	public void getSubCategories_withNameAndProductCategoryIdParameter_shouldReturnListOfSubCategories()
			throws Exception {
		List<SubCategory> subCategories = new ArrayList<>();
		subCategories.add(getSubCategoryInstance());
		subCategories.add(getSubCategoryInstance());

		when(subCategoryService.findSubCategoryByName(eq(page), anyString())).thenReturn(subCategories);
		mockMvc.perform(get(BASE_URL).param("name", "subcategory").param("productCategoryId", "1"))
		.andExpect(status().isOk());

		verify(subCategoryService, times(1)).findSubCategoryByName(any(Pageable.class), anyString());
	}

	@Test
	public void getSubCategories_withNullNameParameter_shouldReturnListOfSubCategories( ) throws Exception {
		List<SubCategory> subCategories = new ArrayList<>();
		subCategories.add(getSubCategoryInstance());
		subCategories.add(getSubCategoryInstance());

		when(subCategoryService.findAllPageable(eq(page), anyLong())).thenReturn(subCategories);
		mockMvc.perform(get(BASE_URL).param("productCategoryId", "1"))
		.andExpect(status().isOk());

		verify(subCategoryService, times(1)).findAllPageable(any(Pageable.class), anyLong());
	}

	@Test
	public void getSubCategoriesBydId_withValidSubCategoryId_shouldReturnSubCategory() throws Exception {
		SubCategory subCategory = getSubCategoryInstance();
		subCategory.setId(1L);
		when(subCategoryService.findById(anyLong())).thenReturn(Optional.of(subCategory));

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("subCategory")));

		verify(subCategoryService, times(1)).findById(anyLong());
	}

	@Test
	public void getSubCategoriesBydId_withInvalidSubCategoryId_shouldReturnNotFound() throws Exception {

		when(subCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isNotFound());

		verify(subCategoryService, times(1)).findById(anyLong());
	}

	@Test
	public void updateSubCategory_withValidSubCategory_shouldUpdateSuccessfully()
			throws JsonProcessingException, Exception {
		SubCategoryForm subCategoryForm = getSubCategoryForm(1L);
		SubCategory subCategory = getSubCategoryInstance();
		subCategory.setId(1L);

		when(subCategoryService.save(any(SubCategory.class))).thenReturn(subCategory);
		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance()));
		when(subCategoryService.findById(anyLong())).thenReturn(Optional.of(subCategory));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(subCategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("subCategory")));

		verify(subCategoryService, times(1)).findById(anyLong());
		verify(productCategoryService, times(1)).findById(anyLong());
		verify(subCategoryService, times(1)).save(any(SubCategory.class));
	}

	@Test(expected = Exception.class)
	public void updateSubCategory_withoutInvalidProductCategoryId_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubCategoryForm subCategoryForm = getSubCategoryForm(null);
		SubCategory subcategory = getSubCategoryInstance();
		subcategory.setId(1L);

		when(subCategoryService.save(any(SubCategory.class))).thenReturn(subcategory);
		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance()));
		when(subCategoryService.findById(anyLong())).thenReturn(Optional.of(subcategory));

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(subCategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subCategoryService, never()).findById(anyLong());
		verify(productCategoryService, never()).findById(anyLong());
		verify(subCategoryService, never()).save(any(SubCategory.class));
	}

	@Test(expected = Exception.class)
	public void updateSubCategory_withInvalidProductCategoryId_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubCategoryForm subCategoryForm = getSubCategoryForm(12L);
		SubCategory subcategory = getSubCategoryInstance();
		subcategory.setId(1L);

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(subCategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subCategoryService, never()).findById(anyLong());
		verify(productCategoryService, times(1)).findById(anyLong());
		verify(subCategoryService, never()).save(any(SubCategory.class));
	}

	@Test(expected = Exception.class)
	public void updateSubCategory_withInvalidSubcategoryId_shouldThrowException()
			throws JsonProcessingException, Exception {
		SubCategoryForm subcategoryForm = getSubCategoryForm(12L);
		SubCategory subcategory = getSubCategoryInstance();
		subcategory.setId(1L);

		when(productCategoryService.findById(anyLong())).thenReturn(Optional.of(getProductCategoryInstance()));
		when(subCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(patch(BASE_URL + "/1").content(objecMapper.writeValueAsString(subcategoryForm))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		verify(subCategoryService, times(1)).findById(anyLong());
		verify(productCategoryService, times(1)).findById(anyLong());
		verify(subCategoryService, never()).save(any(SubCategory.class));
	}

	@Test
	public void removeSubCategory_withValidParameters_shouldRemoveSuccessfully() throws Exception {
		SubCategory subCategory = getSubCategoryInstance();
		subCategory.setId(1L);

		when(subCategoryService.findById(anyLong())).thenReturn(Optional.of(subCategory));
		when(productService.isThereAnyProductWithSubCategoryId(anyLong())).thenReturn(false);

		mockMvc.perform(delete(BASE_URL + "/1"));

		verify(subCategoryService, times(1)).remove(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeSubCategory_withInvalidSubCategoryId_shouldThrowException() throws Exception {

		when(subCategoryService.findById(anyLong())).thenReturn(Optional.empty());

		mockMvc.perform(delete(BASE_URL + "/1"));

		verify(subCategoryService, never()).remove(anyLong());
		verify(productService, never()).isThereAnyProductWithSubCategoryId(anyLong());
	}

	@Test(expected = Exception.class)
	public void removeSubCategory_whenThereIsProductWithSubCategoryId_shouldThrowException() throws Exception {
		SubCategory subCategory = getSubCategoryInstance();
		subCategory.setId(1L);
		when(subCategoryService.findById(anyLong())).thenReturn(Optional.of(subCategory));
		when(productService.isThereAnyProductWithSubCategoryId(anyLong())).thenReturn(true);

		mockMvc.perform(delete(BASE_URL + "/1"));

		verify(subCategoryService, never()).remove(anyLong());
		verify(productService, times(1)).isThereAnyProductWithSubCategoryId(anyLong());
	}

	private SubCategory getSubCategoryInstance() {
		final SubCategory subcategory = new SubCategory();
		subcategory.setName("subCategory");
		subcategory.setProductCategory(getProductCategoryInstance());
		return subcategory;
	}

	private ProductCategory getProductCategoryInstance() {
		final ProductCategory productCategory = new ProductCategory();
		productCategory.setId(1L);
		productCategory.setName("productCategory");
		return productCategory;
	}

	private static SubCategoryForm getSubCategoryForm(final Long productCategoryId) {
		SubCategoryForm subCategoryForm = new SubCategoryForm();
		subCategoryForm.setName("subCategory");
		subCategoryForm.setProductCategoryId(productCategoryId);
		return subCategoryForm;
	}
}
