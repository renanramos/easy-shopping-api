/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.model.ProductCategory;

/**
 * @author renan.ramos
 *
 */
public class ProductCategoryBuilder {

	
	private ProductCategory productCategory;
	
	/**
	 * 
	 */
	private ProductCategoryBuilder() {
		this.productCategory = new ProductCategory();
	}
	
	public static ProductCategoryBuilder builder( ) {
		return new ProductCategoryBuilder();
	}
	
	public ProductCategoryBuilder withName(String name) {
		this.productCategory.setName(name);
		return this;
	}
	
	public ProductCategory build() {
		return this.productCategory;
	}
}
