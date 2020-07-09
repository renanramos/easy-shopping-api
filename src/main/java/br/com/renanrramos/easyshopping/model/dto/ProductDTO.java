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
import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.Store;

/**
 * @author renan.ramos
 *
 */
public class ProductDTO {

	private String name;
	
	private String description;
	
	private double price;
	
	private ProductCategory productCategory;
	
	private Store store;
	
	public ProductDTO() {
	}

	public ProductDTO(Product product) {
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.productCategory = product.getProductCategory();
		this.store = product.getStore();
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

	public ProductCategory getProductCategory() {
		return productCategory;
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

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
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
		return "ProductDTO [name=" + name + ", description=" + description + ", price=" + price + ", productCategory="
				+ productCategory + ", store=" + store + "]";
	}
}
