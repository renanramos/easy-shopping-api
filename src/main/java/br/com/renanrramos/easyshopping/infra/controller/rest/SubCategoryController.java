/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.domain.constants.PaginationConstantValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.SubCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.SubCategoryForm;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.infra.delegate.ProductDelegate;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductCategoryEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.SubCategory;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.SubCategoryMapper;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.SubCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
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
    private ProductDelegate productDelegate;

    private URI uri;

    @ResponseBody
    @PostMapping
    @Transactional
    @ApiOperation(value = "Save a new subCategory")
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<SubCategoryDTO> saveSubCategory(@Valid @RequestBody SubCategoryForm subCategoryForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {

        if (subCategoryForm.getProductCategoryId() == null) {
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<ProductCategoryEntity> productCategoryOptional = productCategoryService.findById(subCategoryForm.getProductCategoryId());

        if (!productCategoryOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_CATEGORY_NOT_FOUND);
        }

        ProductCategoryEntity productCategory = productCategoryOptional.get();
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
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {
        Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
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
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<ProductCategoryEntity> productCatgoryOptional = productCategoryService.findById(subCategoryForm.getProductCategoryId());

        if (!productCatgoryOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_CATEGORY_NOT_FOUND);
        }

        ProductCategoryEntity productCategory = productCatgoryOptional.get();

        Optional<SubCategory> currentSubCategory = subCategoryService.findById(subCategoryId);

        if (!currentSubCategory.isPresent()) {
            throw new EasyShoppingException(ExceptionConstantMessages.SUBCATEGORY_NOT_FOUND);
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
            throw new EasyShoppingException(ExceptionConstantMessages.SUBCATEGORY_NOT_FOUND);
        }

        // TODO: Fix method bellow
//        if (productDelegate.isThereAnyProductWithSubCategoryId(subCategoryId)) {
//            throw new EasyShoppingException(ExceptionConstantMessages.CANNOT_REMOVE_PRODUCT_CATEGORY_IN_USE);
//        }

        subCategoryService.remove(subCategoryId);
        return ResponseEntity.ok().build();
    }
}
