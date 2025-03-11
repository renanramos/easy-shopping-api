/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.constants.PaginationConstantValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductCategoryForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.delegate.ProductCategoryDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;

/**
 * @author renan.ramos
 */
@RestController
@RequestMapping(path = "api/product-categories", produces = "application/json")
@Api(tags = "Product categories")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryDelegate productCategoryDelegate;

    private URI uri;

    @ResponseBody
    @Transactional
    @PostMapping
    @ApiOperation(value = "Save a new product category")
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<ProductCategoryDTO> saveProductCategory(@Valid @RequestBody ProductCategoryForm productCategoryForm,
                                                                  final UriComponentsBuilder uriBuilder) {
        final ProductCategoryDTO productCategoryDTO = productCategoryDelegate.save(productCategoryForm);
        return ResponseEntity.created(buildUri(uriBuilder, productCategoryDTO)).body(productCategoryDTO);
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all product categories")
    public ResponseEntity<PageResponse<ProductCategoryDTO>> getProductCategories(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {
        return ResponseEntity.ok(
                productCategoryDelegate.findProductCategories(
                        new ParametersRequest(pageNumber, pageSize, sortBy), name));
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get a product category by id")
    public ResponseEntity<ProductCategoryDTO> getProductCategoryById(@PathVariable("id") Long productCategoryId) {
        return ResponseEntity.ok(productCategoryDelegate.findById(productCategoryId));
    }

    @ResponseBody
    @PatchMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Update a product category")
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<ProductCategoryDTO> updateProductCategory(@PathVariable("id") Long productCategoryId,
                                                                    @RequestBody ProductCategoryForm productCategoryForm,
                                                                    final UriComponentsBuilder uriBuilder) {
        final ProductCategoryDTO productCategoryDTO =
                productCategoryDelegate.update(productCategoryForm, productCategoryId);
        return ResponseEntity
                .accepted()
                .location(buildUri(uriBuilder, productCategoryDTO))
                .body(productCategoryDTO);
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Remove a product category")
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<ProductCategoryDTO> removeProductCategory(@PathVariable("id") final Long productCategoryId) {

        // TODO: apply validation in usecase layer
//        if (productService.isThereAnyProductWithSubCategoryId(productCategoryId)) {
//            throw new EasyShoppingException(ExceptionConstantMessages.CANNOT_REMOVE_PRODUCT_CATEGORY_IN_USE);
//        }
        productCategoryDelegate.remove(productCategoryId);
        return ResponseEntity.ok().build();
    }

    private static URI buildUri(final UriComponentsBuilder uriBuilder, final ProductCategoryDTO productCategoryDTO) {
        return uriBuilder.path("/product-categories/{id}").buildAndExpand(productCategoryDTO).encode().toUri();
    }
}
