/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.builder.ProductCategoryBuilder;

/**
 * @author renan.ramos
 *
 */
public class ProductCategoryForm {

	private String name;
	
	public ProductCategoryForm() {
	}

	public ProductCategoryForm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ProductCategory converterProductCategoryFormToProductCategory(ProductCategoryForm productCategoryForm) {
		return ProductCategoryBuilder.builder()
				.withName(productCategoryForm.getName())
				.build();
	}
	
	@Override
	public String toString() {
		return "ProductCategoryForm [name=" + name + "]";
	}
		
}
