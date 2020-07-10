/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.dto.ProductDTO;
import br.com.renanrramos.easyshopping.model.form.ProductForm;
import br.com.renanrramos.easyshopping.service.impl.ProductCategoryService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import br.com.renanrramos.easyshopping.service.impl.StoreService;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "products", produces = "application/json")
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
	public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductForm productForm, UriComponentsBuilder uriComponentsBuilder) {
		
		Long productCategoryId = productForm.getProductCategoryId();
		Long storeId = productForm.getStoreId();
		
		Store store = new Store();
		Optional<Store> storeOptional = storeService.findById(storeId); 
		if (storeOptional.isPresent()) {
			store = storeOptional.get();
		} else {
			// TODO: throws an exception here
		}
		
		ProductCategory productCategory = new ProductCategory();
		
		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(productCategoryId);
		if (productCategoryOptional.isPresent()) {
			productCategory = productCategoryOptional.get();
		} else {
			// TODO: throws an exception here
		}
		
		Product product = ProductForm.converterProductFormToProduct(productForm);
		product.setProductCategory(productCategory);
		product.setStore(store);

		product = productService.save(product);
		
		uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(product.getId()).encode().toUri();
		
		return ResponseEntity.created(uri).body(ProductDTO.convertProductToProductDTO(product));
	}
	
	@ResponseBody
	@GetMapping
	public ResponseEntity<List<ProductDTO>> getProducts() {
		List<Product> products = productService.findAll();
		
		if (products.isEmpty()) {
			return ResponseEntity.ok(new ArrayList<ProductDTO>()).notFound().build();
		}
		return ResponseEntity.ok(ProductDTO.converterProductListToProductDTOList(products));		
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long productId) {
		Optional<Product> productOptional = productService.findById(productId);
		if(productOptional.isPresent()) {
			return ResponseEntity.ok(ProductDTO.convertProductToProductDTO(productOptional.get()));
		}
		return ResponseEntity.ok(new ProductDTO()).notFound().build();
	}
	
	@ResponseBody
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long productId, @Valid @RequestBody ProductForm productForm,
			UriComponentsBuilder uriBuilder) {
		if (productId == null) {
			return ResponseEntity.badRequest().build();
		}
		
		Optional<Product> productOptional = productService.findById(productId);
		
		if (productOptional.isPresent()) {
			Product product = ProductForm.converterProductFormToProduct(productForm);
			product.setId(productId);
			product = productService.save(product);
			uri = uriBuilder.path("/products/{id}").buildAndExpand(productId).encode().toUri();
			return ResponseEntity.accepted().location(uri).body(ProductDTO.convertProductToProductDTO(product));
		}
		return ResponseEntity.notFound().build();
	}
}
