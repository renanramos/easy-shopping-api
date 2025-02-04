/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.core.domain.constants.PaginationConstantValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductCategoryForm;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductCategory;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.ProductCategoryMapper;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<ProductCategoryDTO> saveProductCategory(@Valid @RequestBody ProductCategoryForm productCategoryForm,
                                                                  UriComponentsBuilder uriBuilder) {
        ProductCategory productCategory = ProductCategoryMapper.INSTANCE.mapProductCategoryFormToProductCategory(productCategoryForm);
        productCategory = productCategoryService.save(productCategory);

        if (productCategory.getId() == null) {
            throw new InternalError(ExceptionConstantMessages.INTERNAL_ERROR);
        }

        uri = uriBuilder.path("/product-categories/{id}").buildAndExpand(productCategory.getId()).encode().toUri();
        return ResponseEntity.created(uri)
                .body(ProductCategoryMapper.INSTANCE.mapProductCategoryToProductCategoryDTO(productCategory));
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all product categories")
    public ResponseEntity<List<ProductCategoryDTO>> getProductCategories(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {
        Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();
        List<ProductCategory> productCategories =
                (name == null || name.isEmpty()) ?
                        productCategoryService.findAllPageable(page, null) :
                        productCategoryService.findAllProductCategoriesByName(page, name);
        return ResponseEntity
                .ok(ProductCategoryMapper.INSTANCE
                        .mapProductCategoryListToProductCategoryDTOList(productCategories));
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get a product category by id")
    public ResponseEntity<ProductCategoryDTO> getProductCategoryById(@PathVariable("id") Long productCategoryId) {
        Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(productCategoryId);
        if (productCategoryOptional.isPresent()) {
            ProductCategory productCategory = productCategoryOptional.get();
            return ResponseEntity.ok(ProductCategoryMapper.INSTANCE
                    .mapProductCategoryToProductCategoryDTO(productCategory));
        }
        return ResponseEntity.notFound().build();
    }

    @ResponseBody
    @PatchMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Update a product category")
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<ProductCategoryDTO> updateProductCategory(@PathVariable("id") Long productCategoryId,
                                                                    @RequestBody ProductCategoryForm productCategoryForm, UriComponentsBuilder uriBuilder) {
        Optional<ProductCategory> currentProductCategory = productCategoryService.findById(productCategoryId);

        if (!currentProductCategory.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_CATEGORY_NOT_FOUND);
        }


        ProductCategory productCategory = currentProductCategory.get();
        ProductCategoryMapper.INSTANCE
                .mapProductCategoryFormToUpdateProductCategory(productCategory, productCategoryForm);
        productCategory.setId(productCategoryId);
        ProductCategoryDTO productCategoryDTO = ProductCategoryMapper.INSTANCE
                .mapProductCategoryToProductCategoryDTO(productCategoryService.update(productCategory));
        uri = uriBuilder.path("/product-categories/{id}").buildAndExpand(productCategoryDTO).encode().toUri();

        return ResponseEntity.accepted().location(uri).body(productCategoryDTO);
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Remove a product category")
    @RolesAllowed("easy-shopping-admin")
    public ResponseEntity<ProductCategoryDTO> removeProductCategory(@PathVariable("id") Long productCategoryId)
            throws EasyShoppingException {
        Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(productCategoryId);

        if (!productCategoryOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_CATEGORY_NOT_FOUND);
        }

        if (productService.isThereAnyProductWithSubCategoryId(productCategoryId)) {
            throw new EasyShoppingException(ExceptionConstantMessages.CANNOT_REMOVE_PRODUCT_CATEGORY_IN_USE);
        }

        productCategoryService.remove(productCategoryId);

        return ResponseEntity.ok().build();
    }
}
