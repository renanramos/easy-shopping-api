/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.builder.StoreBuilder;
/**
 * @author renan.ramos
 *
 */
public class ProductDTO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private double price;
	
	private String productCategoryName;
	
	private Store store;
	
	public ProductDTO() {
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.productCategoryName = product.getProductCategory().getName();
		this.store = getStoreInfo(product.getStore());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public Store getStore() {
		return store;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setProductCategory(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	public static List<ProductDTO> converterProductListToProductDTOList(List<Product> products) {
		return products.stream().map(ProductDTO::new).collect(Collectors.toList());
	}
	
	public static ProductDTO convertProductToProductDTO(Product product) {
		return new ProductDTO(product);
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", productCategoryName=" + productCategoryName + ", store=" + store + "]";
	}

	private static Store getStoreInfo(Store store) {
		return StoreBuilder.builder()
				.withCorporateName(store.getCorporateName())
				.withName(store.getName())
				.withRegisteredNumber(store.getRegisteredNumber())
				.build();
	}
	
}
