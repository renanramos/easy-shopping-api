/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 03/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

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
	
	private Long productSubcategoryId;
	
	private Long storeId;

	private String companyId;

	public ProductForm() {
		// Intentionally empty
	}

	public ProductForm(String name, String description, double price, Long productSubcategoryId, Long storeId, String companyId) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.productSubcategoryId = productSubcategoryId;
		this.storeId = storeId;
		this.companyId = companyId;
	}

	public static Product converterProductFormToProduct(ProductForm productForm) {
		return ProductBuilder.builder()
				.withName(productForm.getName())
				.withDescription(productForm.getDescription())
				.withPrice(productForm.getPrice())
				.build();
	}

	public static Product converterProductFormUpdateToProduct(ProductForm productForm, Product product) {
		return ProductBuilder.builder()
				.withName(Optional.ofNullable(productForm.getName()).orElse(product.getName()))
				.withDescription(Optional.ofNullable(productForm.getDescription()).orElse(product.getDescription()))
				.withPrice(Optional.ofNullable(productForm.getPrice()).orElse(product.getPrice()))
				.build();
	}
	
	@Override
	public String toString() {
		return "ProductForm [name=" + name + ", description=" + description + ", price=" + price
				+ ", productSubcategoryId=" + productSubcategoryId + ", storeId=" + storeId + ", companyId=" + companyId
				+ "]";
	}	
}
