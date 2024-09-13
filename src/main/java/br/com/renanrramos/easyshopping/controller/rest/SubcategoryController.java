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

import br.com.renanrramos.easyshopping.interfaceadapter.mapper.SubcategoryMapper;
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
import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.model.dto.SubcategoryDTO;
import br.com.renanrramos.easyshopping.model.form.SubcategoryForm;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import br.com.renanrramos.easyshopping.service.impl.SubcategoryService;
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
public class SubcategoryController {

	@Autowired
	private SubcategoryService subcategoryService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductService productService;

	private URI uri;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new subcategory")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<SubcategoryDTO> saveSubcategory(@Valid @RequestBody SubcategoryForm subcategoryForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {

		if (subcategoryForm.getProductCategoryId() == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(subcategoryForm.getProductCategoryId());

		if (!productCategoryOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}

		ProductCategory productCategory = productCategoryOptional.get();
		Subcategory subcategory = SubcategoryMapper.INSTANCE.mapSubcategoryFormToSubcategory(subcategoryForm);
		subcategory.setProductCategory(productCategory);
		subcategory = subcategoryService.save(subcategory);
		uri = uriBuilder.path("/subcategories/{id}").buildAndExpand(subcategory.getId()).encode().toUri();

		return ResponseEntity.created(uri).body(SubcategoryMapper.INSTANCE.mapSubcategoryToSubcategoryDTO(subcategory));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all subcategories")
	public ResponseEntity<List<SubcategoryDTO>> getSubcategories(
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
		List<Subcategory> subcategories =
				(name == null || name.isEmpty()) ?
						subcategoryService.findAllPageable(page, productCategoryId) :
							subcategoryService.findSubcategoryByName(page, name);
						return ResponseEntity.ok(SubcategoryMapper.INSTANCE
								.mapSubcategoryListToSubcategoryDTOList(subcategories));
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get subcategory by id")
	public ResponseEntity<SubcategoryDTO> getSubcategoriesBydId(@PathVariable("id") Long subcategoryId) {
		Optional<Subcategory> subcategoryOptional = subcategoryService.findById(subcategoryId);
        return subcategoryOptional.map(subcategory ->
				ResponseEntity.ok(SubcategoryMapper.INSTANCE.mapSubcategoryToSubcategoryDTO(subcategory)))
				.orElseGet(() -> ResponseEntity.notFound().build());
    }

	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update a subcategory")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<SubcategoryDTO> updateSubcategory(@PathVariable("id") Long subcategoryId, @RequestBody SubcategoryForm subcategoryForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {

		if (subcategoryForm.getProductCategoryId() == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<ProductCategory> productCatgoryOptional = productCategoryService.findById(subcategoryForm.getProductCategoryId());

		if (!productCatgoryOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}

		ProductCategory productCategory = productCatgoryOptional.get();

		Optional<Subcategory> currentSubcategory = subcategoryService.findById(subcategoryId);

		if (!currentSubcategory.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.SUBCATEGORY_NOT_FOUND);
		}

		Subcategory subcategory = currentSubcategory.get();
		SubcategoryMapper.INSTANCE.mapSubcategoryFormToUpdateSubcategory(subcategory, subcategoryForm);
		subcategory.setId(subcategoryId);
		subcategory.setProductCategory(productCategory);
		subcategory = subcategoryService.save(subcategory);

		uri = uriBuilder.path("/subcategories/{id}").buildAndExpand(subcategory.getId()).encode().toUri();

		return ResponseEntity.accepted().location(uri).body(SubcategoryMapper.INSTANCE.mapSubcategoryToSubcategoryDTO(subcategory));
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove a subcategory")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<SubcategoryDTO> removeSubcategory(@PathVariable("id") Long subcategoryId) throws EasyShoppingException {
		Optional<Subcategory> subcategoryOptional = subcategoryService.findById(subcategoryId);

		if (!subcategoryOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.SUBCATEGORY_NOT_FOUND);
		}

		if (productService.isThereAnyProductWithSubcategoryId(subcategoryId)) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CANNOT_REMOVE_PRODUCT_CATEGORY_IN_USE);
		}

		subcategoryService.remove(subcategoryId);
		return ResponseEntity.ok().build();
	}
}
