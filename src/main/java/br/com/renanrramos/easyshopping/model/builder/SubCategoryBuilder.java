/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.SubCategory;

/**
 * @author renan.ramos
 *
 */
public class SubCategoryBuilder {

	private SubCategory subcategory;

	public SubCategoryBuilder() {
		this.subcategory = new SubCategory();
	}

	public SubCategoryBuilder builder() {
		return new SubCategoryBuilder();
	}

	public SubCategoryBuilder withName(String name) {
		this.subcategory.setName(name);
		return this;
	}

	public SubCategoryBuilder withProductCategory(ProductCategory productCategory) {
		this.subcategory.setProductCategory(productCategory);
		return this;
	}

	public SubCategory build() {
		return this.subcategory;
	}
}
