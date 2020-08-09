/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.Subcategory;

/**
 * @author renan.ramos
 *
 */
public class SubcategoryBuilder {

	private Subcategory subcategory;

	public SubcategoryBuilder() {
		this.subcategory = new Subcategory();
	}

	public SubcategoryBuilder builder() {
		return new SubcategoryBuilder();
	}

	public SubcategoryBuilder withName(String name) {
		this.subcategory.setName(name);
		return this;
	}

	public SubcategoryBuilder withProductCategory(ProductCategory productCategory) {
		this.subcategory.setProductCategory(productCategory);
		return this;
	}

	public Subcategory build() {
		return this.subcategory;
	}
}
