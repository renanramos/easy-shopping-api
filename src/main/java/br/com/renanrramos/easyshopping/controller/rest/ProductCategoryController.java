/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestHeader;
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
import br.com.renanrramos.easyshopping.model.dto.ProductCategoryDTO;
import br.com.renanrramos.easyshopping.model.form.ProductCategoryForm;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/product-categories", produces = "application/json")
@Api(tags = "Product categories")
@CrossOrigin(origins = "*")
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductService productService;

	private URI uri;
	
	@ResponseBody
	@Transactional
	@PostMapping
	@ApiOperation(value = "Save a new product category")
	public ResponseEntity<ProductCategoryDTO> saveProductCategory(@Valid @RequestBody ProductCategoryForm productCategoryForm, 
			UriComponentsBuilder uriBuilder) {
		ProductCategory productCategory = ProductCategoryForm.converterProductCategoryFormToProductCategory(productCategoryForm);
		productCategory = productCategoryService.save(productCategory);

		if (productCategory.getId() == null) {
			throw new InternalError(ExceptionMessagesConstants.INTERNAL_ERROR);
		}

		uri = uriBuilder.path("/productCategories/{id}").buildAndExpand(productCategory.getId()).encode().toUri();
		return ResponseEntity.created(uri)
				.body(ProductCategoryDTO.converterProductCategoryToProductCategoryDTO(productCategory));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all product categories")
	public ResponseEntity<List<ProductCategoryDTO>> getProductCategories(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber, 
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
		Pageable page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();
		List<ProductCategory> productCategories = 
				(name == null) ?
				productCategoryService.findAllPageable(page, null) :
				productCategoryService.findAllProductCategoriesByName(page, name);
		return ResponseEntity.ok(ProductCategoryDTO.converterProductCategoryListToProductCategoryDTOList(productCategories));
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get a product category by id")
	public ResponseEntity<ProductCategoryDTO> getProductCategoryById(@PathVariable("id") Long productCategoryId) {
		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(productCategoryId);
		if (productCategoryOptional.isPresent()) {
			ProductCategory productCategory = productCategoryOptional.get();
			return ResponseEntity.ok(ProductCategoryDTO.converterProductCategoryToProductCategoryDTO(productCategory));
		}
		return ResponseEntity.notFound().build();
	}
	
	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update a product category")
	public ResponseEntity<ProductCategoryDTO> updateProductCategory(@PathVariable("id") Long productCategoryId, 
			@RequestBody ProductCategoryForm productCategoryForm, UriComponentsBuilder uriBuilder) {
		Optional<ProductCategory> currentProductCategory = productCategoryService.findById(productCategoryId);

		if (!currentProductCategory.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}

		ProductCategory productCategory = ProductCategoryForm.converterProductCategoryFormUpdateToProductCategory(productCategoryForm, currentProductCategory.get());
		productCategory.setId(productCategoryId);
		ProductCategoryDTO productCategoryDTO = ProductCategoryDTO.converterProductCategoryToProductCategoryDTO(productCategoryService.save(productCategory));
		uri = uriBuilder.path("/productCategories/{id}").buildAndExpand(productCategoryDTO).encode().toUri();

		return ResponseEntity.accepted().location(uri).body(productCategoryDTO);
	}
	
	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove a product category")
	public ResponseEntity<ProductCategoryDTO> removeProductCategory(@PathVariable("id") Long productCategoryId) throws EasyShoppingException {
		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(productCategoryId);

		if (!productCategoryOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}
		
		if (productService.isThereAnyProductWithSubcategoryId(productCategoryId)) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CANNOT_REMOVE_PRODUCT_CATEGORY_IN_USE);
		}

		productCategoryService.remove(productCategoryId);

		return ResponseEntity.ok().build();
	}
}
