/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import java.util.List;

/**
 * @author renan.ramos
 *
 */
public class ProductCategory {

	private Long id;
	private String name;
	private List<SubCategory> subcategories;
	
	public ProductCategory() {
		// Intentionally empty
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SubCategory> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<SubCategory> subcategories) {
		this.subcategories = subcategories;
	}

	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", name=" + name + "]";
	}

}
