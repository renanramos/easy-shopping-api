/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 03/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.builder.ProductBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductForm {

	private String name;
	
	private String description;
	
	private double price;
	
	private Long productCategoryId;
	
	private Long storeId;

	public ProductForm() {
		// Intentionally empty
	}

	public ProductForm(String name, String description, double price, Long productCategoryId, Long storeId) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.productCategoryId = productCategoryId;
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
