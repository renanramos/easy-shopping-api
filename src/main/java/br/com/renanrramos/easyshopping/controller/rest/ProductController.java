/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductImage;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.model.dto.ProductDTO;
import br.com.renanrramos.easyshopping.model.dto.ProductImageDTO;
import br.com.renanrramos.easyshopping.model.form.ProductForm;
import br.com.renanrramos.easyshopping.model.form.ProductImageForm;
import br.com.renanrramos.easyshopping.service.impl.ProductImageService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import br.com.renanrramos.easyshopping.service.impl.StoreService;
import br.com.renanrramos.easyshopping.service.impl.SubcategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
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
	private SubcategoryService productCategoryService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private ProductImageService productImageService;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new product")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductForm productForm, UriComponentsBuilder uriComponentsBuilder) {

		if (productForm.getProductSubcategoryId() == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST);
		}

		if (productForm.getStoreId() == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.STORE_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Subcategory> productCategoryOptional = productCategoryService.findById(productForm.getProductSubcategoryId());

		if (!productCategoryOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}

		Optional<Store> storeOptional = storeService.findById(productForm.getStoreId());

		if (!storeOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.STORE_NOT_FOUND);
		}

		Store store = storeOptional.get();

		Subcategory productCategory = productCategoryOptional.get();

		Product product = ProductForm.converterProductFormToProduct(productForm);
		product.setSubcategory(productCategory);
		product.setStore(store);
		product.setPublished(false);
		product.setCompanyId(productForm.getCompanyId());

		product = productService.save(product);

		uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(product.getId()).encode().toUri();

		return ResponseEntity.created(uri).body(ProductDTO.convertProductToProductDTO(product));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(
			@RequestParam(name = "published", required = false) boolean onlyPublishedProducts,
			@RequestParam(required = false) Long storeId,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
		page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();
		List<Product> products = productService.findAllPageable(page, storeId);
		return onlyPublishedProducts
				? ResponseEntity.ok(ProductDTO.converterPublishedProductListToProductDTOList(products))
						: ResponseEntity.ok(ProductDTO.converterProductListToProductDTOList(products));
	}

	@ResponseBody
	@GetMapping(path = "/search")
	@ApiOperation(value = "Search all products by product category")
	public ResponseEntity<List<ProductDTO>> searchProductsByProductCategory(
			@RequestParam(name = "published", required = false) boolean onlyPublishedProducts,
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy, Principal principal) {
		page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();
		List<Product> products = productService.searchProductByName(page, name, principal);

		return onlyPublishedProducts
				? ResponseEntity.ok(ProductDTO.converterPublishedProductListToProductDTOList(products))
						: ResponseEntity.ok(ProductDTO.converterProductListToProductDTOList(products));
	}

	@ResponseBody
	@GetMapping("/subcategory")
	@ApiOperation(value = "Get all products")
	public ResponseEntity<List<ProductDTO>> getAllProductsBySubcategory(
			@RequestParam(name = "published", required = false) boolean onlyPublishedProducts,
			@RequestParam(required = false) Long subcategoryId,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
		page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();
		List<Product> products = productService.getProductsBySubcategoryId(page, subcategoryId);
		return onlyPublishedProducts
				? ResponseEntity.ok(ProductDTO.converterPublishedProductListToProductDTOList(products))
						: ResponseEntity.ok(ProductDTO.converterProductListToProductDTOList(products));
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
	@PatchMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Update a product")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long productId, @Valid @RequestBody ProductForm productForm,
			UriComponentsBuilder uriBuilder) {

		if (productId == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
		}

		if (productForm.getProductSubcategoryId() == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST);
		}

		if (productForm.getStoreId() == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.STORE_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Product> currentProduct = productService.findById(productId);

		if (!currentProduct.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_NOT_FOUND);
		}

		Optional<Subcategory> productCategoryOptional = productCategoryService.findById(productForm.getProductSubcategoryId());

		if (!productCategoryOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_CATEGORY_NOT_FOUND);
		}

		Subcategory productCategory = productCategoryOptional.get();

		Optional<Store> storeOptional = storeService.findById(productForm.getStoreId());

		if (!storeOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.STORE_NOT_FOUND);
		}

		Store store = storeOptional.get();

		Product product = ProductForm.converterProductFormUpdateToProduct(productForm, currentProduct.get());
		product.setSubcategory(productCategory);
		product.setStore(store);
		product.setId(productId);
		product.setCompanyId(productForm.getCompanyId());
		product = productService.save(product);

		uri = uriBuilder.path("/products/{id}").buildAndExpand(productId).encode().toUri();

		return ResponseEntity.accepted().location(uri).body(ProductDTO.convertProductToProductDTO(product));
	}

	@ResponseBody
	@PatchMapping("/publish/{id}")
	@Transactional
	@ApiOperation(value = "Update a product")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<ProductDTO> publishProduct(@PathVariable("id") Long productId,
			UriComponentsBuilder uriBuilder) {

		Optional<Product> productOptional = this.productService.findById(productId);

		Product product = productOptional.get();
		product.setPublished(true);
		product = productService.save(product);
		uri = uriBuilder.path("/products/{id}").buildAndExpand(productId).encode().toUri();
		return ResponseEntity.accepted().location(uri).body(ProductDTO.convertProductToProductDTO(product));
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Remove a product")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable("id") Long productId) {
		Optional<Product> productOptional = productService.findById(productId);

		if (!productOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_NOT_FOUND);
		}
		productService.remove(productId);
		return ResponseEntity.accepted().build();
	}

	@ResponseBody
	@PostMapping(path = "/images/{id}/upload", consumes=MediaType.APPLICATION_JSON_VALUE)
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<?> uploadProductImage(@PathVariable("id")Long productId, @RequestBody ProductImageForm productImageForm, UriComponentsBuilder uriComponentsBuilder) throws EasyShoppingException, IOException {
		Long productFormId = productImageForm.getProductId();

		if (!productFormId.equals(productId)) {
			throw new EasyShoppingException(ExceptionMessagesConstants.WRONG_PRODUCT_ID);
		}

		Optional<Product> productOptional = productService.findById(productId);

		if (!productOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_NOT_FOUND);
		}

		byte[] picture = productImageForm.getPicture();

		if (picture == null || picture.length == 0) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.INVALID_FILE);
		}

		ProductImage productImage = ProductImageForm.convertProductImageFormToProductImate(productImageForm);
		productImage.setProduct(productOptional.get());

		productImage = productImageService.save(productImage);
		if (productImage.getId() != null) {
			uri = uriComponentsBuilder.path("/products/images/{id}").buildAndExpand(productImage.getProduct().getId()).encode().toUri();
			return ResponseEntity.created(uri).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@ResponseBody
	@GetMapping(path = "/images/{id}")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<List<ProductImageDTO>> getProductImage(@PathVariable("id")Long productId) {

		Optional<Product> productOptional = productService.findById(productId);

		if (!productOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_NOT_FOUND);
		}

		List<ProductImage> productImages = productImageService.findProductImageByProductId(page, productId);
		return ResponseEntity.ok(ProductImageDTO.converterProducImageListToProductImageDTOList(productImages));
	}
}
