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
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductImageDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductImageForm;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Product;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductImage;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Store;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.SubCategory;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.ProductImageMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.ProductMapper;
import br.com.renanrramos.easyshopping.service.impl.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
 */
@RestController
@RequestMapping(path = "/api/products", produces = "application/json")
@Api(tags = "Products")
@CrossOrigin(origins = "*")
public class ProductController {

    private URI uri;

    private Pageable page;

    @Autowired
    private ProductService productService;

    @Autowired
    private SubCategoryService productCategoryService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private StockItemService stockItemService;

    @Autowired
    private AuthenticationService authenticationServiceImpl;

    @ResponseBody
    @PostMapping
    @Transactional
    @ApiOperation(value = "Save a new product")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductForm productForm,
                                                  UriComponentsBuilder uriComponentsBuilder) {

        if (productForm.getProductSubCategoryId() == null) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST);
        }

        if (productForm.getStoreId() == null) {
            throw new EntityNotFoundException(ExceptionConstantMessages.STORE_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<SubCategory> productCategoryOptional = productCategoryService.findById(productForm.getProductSubCategoryId());

        if (!productCategoryOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_CATEGORY_NOT_FOUND);
        }

        Optional<Store> storeOptional = storeService.findById(productForm.getStoreId());

        if (!storeOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.STORE_NOT_FOUND);
        }

        Store store = storeOptional.get();

        SubCategory productCategory = productCategoryOptional.get();

        Product product = ProductMapper.INSTANCE.mapProductFormToProduct(productForm);
        product.setSubCategory(productCategory);
        product.setStore(store);
        product.setPublished(false);
        product.setCompanyId(authenticationServiceImpl.getName());

        product = productService.save(product);

        uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(product.getId()).encode().toUri();

        return ResponseEntity.created(uri).body(ProductMapper.INSTANCE.mapProductToProductDTO(product));
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(name = "published", required = false) boolean onlyPublishedProducts,
            @RequestParam(required = false) Long storeId,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {
        page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();
        List<Product> products = productService.findAllPageable(page, storeId);
        return onlyPublishedProducts
                ? ResponseEntity.ok(ProductMapper.INSTANCE.mapPublishedProductListToProductDTOList(products))
                : ResponseEntity.ok(ProductMapper.INSTANCE.mapProductListToProductDTOList(products));
    }

    @ResponseBody
    @GetMapping(path = "/search")
    @ApiOperation(value = "Search all products by product category")
    public ResponseEntity<List<ProductDTO>> searchProductsByProductCategory(
            @RequestParam(name = "published", required = false) boolean onlyPublishedProducts,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {
        page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();
        List<Product> products = productService.searchProductByName(page, name,
                authenticationServiceImpl.getAuthentication());

        return onlyPublishedProducts
                ? ResponseEntity.ok(ProductMapper.INSTANCE.mapPublishedProductListToProductDTOList(products))
                : ResponseEntity.ok(ProductMapper.INSTANCE.mapProductListToProductDTOList(products));
    }

    @ResponseBody
    @GetMapping("/subCategory")
    @ApiOperation(value = "Get all products")
    public ResponseEntity<List<ProductDTO>> getAllProductsBySubCategory(
            @RequestParam(name = "published", required = false) boolean onlyPublishedProducts,
            @RequestParam(required = false) Long subcategoryId,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = PaginationConstantValues.DEFAULT_SORT_VALUE) String sortBy) {
        page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();
        List<Product> products = productService.getProductsBySubCategoryId(page, subcategoryId);
        return onlyPublishedProducts
                ? ResponseEntity.ok(ProductMapper.INSTANCE.mapPublishedProductListToProductDTOList(products))
                : ResponseEntity.ok(ProductMapper.INSTANCE.mapProductListToProductDTOList(products));
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get a product by id")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long productId) {
        Optional<Product> productOptional = productService.findById(productId);
        return productOptional.map(product ->
                        ResponseEntity.ok(ProductMapper.INSTANCE.mapProductToProductDTO(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseBody
    @PatchMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Update a product")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long productId, @Valid @RequestBody ProductForm productForm,
                                                    UriComponentsBuilder uriBuilder) {

        if (productId == null) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
        }

        if (productForm.getProductSubCategoryId() == null) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST);
        }

        if (productForm.getStoreId() == null) {
            throw new EntityNotFoundException(ExceptionConstantMessages.STORE_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<Product> currentProduct = productService.findById(productId);

        if (!currentProduct.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_NOT_FOUND);
        }

        Optional<SubCategory> productCategoryOptional = productCategoryService.findById(productForm.getProductSubCategoryId());

        if (!productCategoryOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_CATEGORY_NOT_FOUND);
        }

        SubCategory productCategory = productCategoryOptional.get();

        Optional<Store> storeOptional = storeService.findById(productForm.getStoreId());

        if (!storeOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.STORE_NOT_FOUND);
        }

        Store store = storeOptional.get();

        Product product = currentProduct.get();
        ProductMapper.INSTANCE.mapProductFormToUpdateProduct(product, productForm);
        product.setSubCategory(productCategory);
        product.setStore(store);
        product.setId(productId);
        product.setCompanyId(authenticationServiceImpl.getName());
        product = productService.save(product);

        uri = uriBuilder.path("/products/{id}").buildAndExpand(productId).encode().toUri();

        return ResponseEntity.accepted()
                .location(uri)
                .body(ProductMapper.INSTANCE.mapProductToProductDTO(product));
    }

    @ResponseBody
    @PatchMapping("/publish/{id}")
    @Transactional
    @ApiOperation(value = "Publish a product")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<ProductDTO> publishProduct(@PathVariable("id") Long productId,
                                                     UriComponentsBuilder uriBuilder) throws EasyShoppingException {

        if (!stockItemService.hasStockItemByProductId(productId)) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_IS_NOT_IN_STOCK);
        }

        Optional<Product> productOptional = productService.findById(productId);

        Product product = productOptional.get();

        product.setPublished(true);
        product = productService.save(product);
        uri = uriBuilder.path("/products/{id}").buildAndExpand(productId).encode().toUri();
        return ResponseEntity.accepted().location(uri).body(ProductMapper.INSTANCE.mapProductToProductDTO(product));
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Remove a product")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable("id") Long productId) {
        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_NOT_FOUND);
        }
        productService.remove(productId);
        return ResponseEntity.accepted().build();
    }

    @ResponseBody
    @PostMapping(path = "/images/{id}/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add image to a product")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<?> uploadProductImage(@PathVariable("id") Long productId, @RequestBody ProductImageForm productImageForm, UriComponentsBuilder uriComponentsBuilder) throws EasyShoppingException, IOException {
        Long productFormId = productImageForm.getProductId();

        if (!productFormId.equals(productId)) {
            throw new EasyShoppingException(ExceptionConstantMessages.WRONG_PRODUCT_ID);
        }

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_NOT_FOUND);
        }

        byte[] picture = productImageForm.getPicture();

        if (picture == null || picture.length == 0) {
            throw new EntityNotFoundException(ExceptionConstantMessages.INVALID_FILE);
        }

        Product product = productOptional.get();
        product.setPublished(false);

        ProductImage productImage = ProductImageMapper.INSTANCE.mapProductImageFormToProductImage(productImageForm);
        productImage.setProduct(product);

        productImage = productImageService.save(productImage);
        if (productImage.getId() != null) {

            productService.update(product);

            uri = uriComponentsBuilder.path("/products/images/{id}").buildAndExpand(productImage.getProduct().getId()).encode().toUri();
            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @ResponseBody
    @GetMapping(path = "/images/{id}")
    @ApiOperation(value = "Get product image by product id")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<List<ProductImageDTO>> getProductImage(@PathVariable("id") Long productId) {

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_NOT_FOUND);
        }

        List<ProductImage> productImages = productImageService.findProductImageByProductId(page, productId);
        return ResponseEntity.ok(ProductImageMapper.INSTANCE.mapProductImageListToProductImageDTOList(productImages));
    }

    @ResponseBody
    @DeleteMapping("/images/{id}")
    @Transactional
    @ApiOperation(value = "Remove a product image")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<ProductImageDTO> removeProductImage(@PathVariable("id") Long productId) {

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_NOT_FOUND);
        }

        Product product = productOptional.get();

        Optional<ProductImage> productImage = productImageService.getImageByProductId(productId);
        if (!productImage.isPresent()) {
            throw new EntityNotFoundException(ExceptionConstantMessages.PRODUCT_IMAGE_NOT_FOUND);
        }
        productImageService.remove(productImage.get().getId());

        product.setPublished(false);
        productService.update(product);

        return ResponseEntity.ok().build();
    }
}
