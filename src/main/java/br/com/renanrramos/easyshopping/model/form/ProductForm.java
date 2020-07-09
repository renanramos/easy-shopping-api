/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 03/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.builder.ProductBuilder;

/**
 * @author renan.ramos
 *
 */
public class ProductForm {

	private String name;
	
	private String description;
	
	private double price;
	
	private Long productCategoryId;
	
	private Long storeId;

	public ProductForm() {
	}

	public ProductForm(String name, String description, double price, Long productCategoryId, Long storeId) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.productCategoryId = productCategoryId;
		this.storeId = storeId;
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

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public Long getStoreId() {
		return storeId;
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

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	
	public static Product converterProductFormToProduct(ProductForm productForm) {
		return ProductBuilder.builder()
				.withName(productForm.getName())
				.withDescription(productForm.getDescription())
				.withPrice(productForm.getPrice())
				.build();
	}

	@Override
	public String toString() {
		return "ProductForm [name=" + name + ", description=" + description + ", price=" + price + ", productCategoryId="
				+ productCategoryId + ", storeId=" + storeId + ", getName()=" + getName() + ", getDescription()="
				+ getDescription() + ", getPrice()=" + getPrice() + ", getProductCategory()=" + getProductCategoryId()
				+ ", getStoreId()=" + getStoreId() + "]";
	}
	
}
