/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import java.util.Set;

/**
 * @author renan.ramos
 *
 */
public class Product {

	private Long id;
	private String name;
	private String description;
	private double price;
	private SubCategory subCategory;
	private Store store;
	private String companyId;
	private Set<ProductImage> images;
	private boolean isPublished;

	public Product() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}

	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean published) {
		isPublished = published;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", subcategory=" + subCategory + ", store=" + store + "]";
	}
}
