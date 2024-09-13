/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.renanrramos.easyshopping.interfaceadapter.mapper.SubCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.SubCategory;
import br.com.renanrramos.easyshopping.model.dto.SubCategoryDTO;
import br.com.renanrramos.easyshopping.model.form.SubCategoryForm;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import br.com.renanrramos.easyshopping.service.impl.SubCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/subcategories", produces = "application/json")
@Api(tags = "Subcategories")
@CrossOrigin(origins = "*")
public class SubCategoryController {

	@Autowired
	private SubCategoryService subCategoryService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductService productService;

	private URI uri;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new subCategory")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<SubCategoryDTO> saveSubCategory(@Valid @RequestBody SubCategoryForm subCategoryForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {

		if (subCategoryForm.getProductCategoryId() == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(subCategoryForm.getProductCategoryId());

		if (!productCategoryOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}

		ProductCategory productCategory = productCategoryOptional.get();
		SubCategory subCategory = SubCategoryMapper.INSTANCE.mapSubCategoryFormToSubCategory(subCategoryForm);
		subCategory.setProductCategory(productCategory);
		subCategory = subCategoryService.save(subCategory);
		uri = uriBuilder.path("/subCategories/{id}").buildAndExpand(subCategory.getId()).encode().toUri();

		return ResponseEntity.created(uri).body(SubCategoryMapper.INSTANCE.mapSubCategoryToSubCategoryDTO(subCategory));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all subCategories")
	public ResponseEntity<List<SubCategoryDTO>> getSubCategories(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Long productCategoryId,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
		Pageable page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();
		List<SubCategory> subCategories =
				(name == null || name.isEmpty()) ?
						subCategoryService.findAllPageable(page, productCategoryId) :
							subCategoryService.findSubCategoryByName(page, name);
						return ResponseEntity.ok(SubCategoryMapper.INSTANCE
								.mapSubCategoryListToSubCategoryDTOList(subCategories));
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get SubCategory by id")
	public ResponseEntity<SubCategoryDTO> getSubCategoriesBydId(@PathVariable("id") Long subCategoryId) {
		Optional<SubCategory> subcategoryOptional = subCategoryService.findById(subCategoryId);
        return subcategoryOptional.map(subCategory ->
				ResponseEntity.ok(SubCategoryMapper.INSTANCE.mapSubCategoryToSubCategoryDTO(subCategory)))
				.orElseGet(() -> ResponseEntity.notFound().build());
    }

	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update a SubCategory")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<SubCategoryDTO> updateSubCategory(@PathVariable("id") Long subCategoryId,
															@RequestBody SubCategoryForm subCategoryForm,
															UriComponentsBuilder uriBuilder) throws EasyShoppingException {

		if (subCategoryForm.getProductCategoryId() == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<ProductCategory> productCatgoryOptional = productCategoryService.findById(subCategoryForm.getProductCategoryId());

		if (!productCatgoryOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}

		ProductCategory productCategory = productCatgoryOptional.get();

		Optional<SubCategory> currentSubCategory = subCategoryService.findById(subCategoryId);

		if (!currentSubCategory.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.SUBCATEGORY_NOT_FOUND);
		}

		SubCategory subCategory = currentSubCategory.get();
		SubCategoryMapper.INSTANCE.mapSubCategoryFormToUpdateSubCategory(subCategory, subCategoryForm);
		subCategory.setId(subCategoryId);
		subCategory.setProductCategory(productCategory);
		subCategory = subCategoryService.save(subCategory);

		uri = uriBuilder.path("/subCategories/{id}").buildAndExpand(subCategory.getId()).encode().toUri();

		return ResponseEntity.accepted().location(uri).body(SubCategoryMapper.INSTANCE.mapSubCategoryToSubCategoryDTO(subCategory));
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove a SubCategory")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<SubCategoryDTO> removeSubCategory(@PathVariable("id") Long subCategoryId) throws EasyShoppingException {
		Optional<SubCategory> subCategoryOptional = subCategoryService.findById(subCategoryId);

		if (!subCategoryOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.SUBCATEGORY_NOT_FOUND);
		}

		if (productService.isThereAnyProductWithSubCategoryId(subCategoryId)) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CANNOT_REMOVE_PRODUCT_CATEGORY_IN_USE);
		}

		subCategoryService.remove(subCategoryId);
		return ResponseEntity.ok().build();
	}
}
