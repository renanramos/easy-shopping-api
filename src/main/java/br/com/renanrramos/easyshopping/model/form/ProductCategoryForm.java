/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.builder.ProductCategoryBuilder;
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
public class ProductCategoryForm {

	private String name;
	
	public ProductCategoryForm() {
		// Intentionlly empty
	}

	public ProductCategoryForm(String name) {
		this.name = name;
	}

	public static ProductCategory converterProductCategoryFormToProductCategory(ProductCategoryForm productCategoryForm) {
		return ProductCategoryBuilder.builder()
				.withName(productCategoryForm.getName())
				.build();
	}

	public static ProductCategory converterProductCategoryFormUpdateToProductCategory(ProductCategoryForm productCategoryForm, ProductCategory currentProductCategory) {
		return ProductCategoryBuilder.builder()
				.withName(Optional.ofNullable(productCategoryForm.getName()).orElse(currentProductCategory.getName()))
				.build();
	}
	
	@Override
	public String toString() {
		return "ProductCategoryForm [name=" + name + "]";
	}
	
}
