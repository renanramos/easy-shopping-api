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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.dto.ProductDTO;
import br.com.renanrramos.easyshopping.model.form.ProductForm;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import br.com.renanrramos.easyshopping.service.impl.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@CrossOrigin
@RequestMapping(path = "api/products", produces = "application/json")
@Api(tags = "Products")
public class ProductController {

	private URI uri;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private StoreService storeService;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new product")
	public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductForm productForm, UriComponentsBuilder uriComponentsBuilder) throws EasyShoppingException {

		if (productForm.getProductCategoryId() == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST);
		}

		if (productForm.getStoreId() == null) {
			throw new EasyShoppingException(ExceptionMessagesConstants.STORE_ID_NOT_FOUND_ON_REQUEST);
		}
		
		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(productForm.getProductCategoryId());
		
		if (!productCategoryOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}

		Optional<Store> storeOptional = storeService.findById(productForm.getStoreId());

		if (!storeOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.STORE_NOT_FOUND);
		}

		Store store = storeOptional.get();
		
		ProductCategory productCategory = productCategoryOptional.get();

		Product product = ProductForm.converterProductFormToProduct(productForm);
		product.setProductCategory(productCategory);
		product.setStore(store);

		product = productService.save(product);
		
		uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(product.getId()).encode().toUri();
		
		return ResponseEntity.created(uri).body(ProductDTO.convertProductToProductDTO(product));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all products")
	public ResponseEntity<List<ProductDTO>> getProducts(
			@RequestParam(required = false) Long storeId,
			@RequestParam(defaultValue = "0") Integer pageNumber, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
		List<Product> products =
				(storeId == null) ?
				productService.findAllPageable(pageNumber, pageSize, sortBy) :
				productService.findProductByStoreId(storeId);
		return ResponseEntity.ok(ProductDTO.converterProductListToProductDTOList(products));		
	}

	@ResponseBody
	@GetMapping(path = "/search")
	@ApiOperation(value = "Get all products by product category")
	public ResponseEntity<List<ProductDTO>> getProductsByProductCategory(
			@RequestParam(required = false, name = "Product category name") String productCategoryName,
			@RequestParam(defaultValue = "0") Integer pageNumber, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
		List<Product> products =
				(productCategoryName == null) ?
				productService.findAllPageable(pageNumber, pageSize, sortBy) :
				productService.findProductByProductCategoryId(productCategoryName);
		return ResponseEntity.ok(ProductDTO.converterProductListToProductDTOList(products));		
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get a product by id")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long productId) {
		Optional<Product> productOptional = productService.findById(productId);
		if(productOptional.isPresent()) {
			return ResponseEntity.ok(ProductDTO.convertProductToProductDTO(productOptional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@ResponseBody
	@PutMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Update a product")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long productId, @Valid @RequestBody ProductForm productForm,
			UriComponentsBuilder uriBuilder) {

		if (productId == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
		}

		if (productForm.getProductCategoryId() == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST);
		}

		if (productForm.getStoreId() == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.STORE_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Product> productOptional = productService.findById(productId);

		if (!productOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_NOT_FOUND);
		}

		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(productForm.getProductCategoryId());
		
		if (!productCategoryOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}

		ProductCategory productCategory = productCategoryOptional.get();

		Optional<Store> storeOptional = storeService.findById(productForm.getStoreId());
		
		if (!storeOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.STORE_NOT_FOUND);
		}

		Store store = storeOptional.get();

		Product product = ProductForm.converterProductFormToProduct(productForm);
		product.setProductCategory(productCategory);
		product.setStore(store);
		product.setId(productId);
		product = productService.save(product);
		
		uri = uriBuilder.path("/products/{id}").buildAndExpand(productId).encode().toUri();
		
		return ResponseEntity.accepted().location(uri).body(ProductDTO.convertProductToProductDTO(product));
	}
	
	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Remove a product")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId) {
		Optional<Product> productOptional = productService.findById(productId);

		if (!productOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_NOT_FOUND);
		}
		productService.remove(productId);
		return ResponseEntity.accepted().build();
	}
}
