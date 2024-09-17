/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;


import java.util.ArrayList;
import java.util.List;

/**
 * @author renan.ramos
 *
 */
public class SubCategory {

	private Long id;
	private String name;
	private ProductCategory productCategory;
	private List<Product> products = new ArrayList<>();

	public SubCategory() {
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

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "SubCategory [id=" + id + ", name=" + name + ", productCategory=" + productCategory.getId() + "]";
	}
}
