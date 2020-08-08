/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.model.dto.SubcategoryDTO;
import br.com.renanrramos.easyshopping.model.form.SubcategoryForm;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.SubcategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@CrossOrigin
@RequestMapping(path = "api/subcategories", produces = "application/json")
@Api(tags = "Subcategories")
public class SubCategoryController {

	@Autowired
	private SubcategoryService subcategoryService;

	@Autowired
	private ProductCategoryService productCategoryService;

	private URI uri;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new sub category")
	public ResponseEntity<SubcategoryDTO> saveSubcategory(@Valid @RequestBody SubcategoryForm subcategoryForm, UriComponentsBuilder uriBuilders) throws EasyShoppingException {

		if (subcategoryForm.getProductCategoryId() == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(subcategoryForm.getProductCategoryId());

		if (productCategoryOptional.isPresent()) {
			ProductCategory productCategory = productCategoryOptional.get();
			Subcategory subcategory = SubcategoryForm.convertSubcategoryFormToSubcategory(subcategoryForm);
			subcategory.setProductCategory(productCategory);
			subcategory = subcategoryService.save(subcategory);
			uri = uriBuilders.path("/subcategories/{id}").buildAndExpand(subcategory.getId()).encode().toUri();
			return ResponseEntity.created(uri).body(SubcategoryDTO.convertSubCategoryToSubCategoryDTO(subcategory));
		} else {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}
	}
}
