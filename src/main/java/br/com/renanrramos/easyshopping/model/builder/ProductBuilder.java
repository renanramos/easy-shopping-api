/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.Store;

/**
 * @author renan.ramos
 *
 */
public class ProductBuilder {

	private Product product;
	
	private ProductBuilder() {
		this.product = new Product();
	}
	
	public static ProductBuilder builder() {
		return new ProductBuilder();
	}
	
	public ProductBuilder withName(String name) {
		this.product.setName(name);
		return this;
	}
	
	public ProductBuilder withDescription(String description) {
		this.product.setDescription(description);
		return this;
	}
	
	public ProductBuilder withPrice(double price) {
		this.product.setPrice(price);
		return this;
	}
	
	public ProductBuilder withStore(Store store) {
		this.product.setStore(store);
		return this;
	}
	
	public ProductBuilder withProductCategory(ProductCategory productCategory) {
		this.product.setProductCategory(productCategory);
		return this;
	}
	
	public Product build() {
		return this.product;
	}
}
