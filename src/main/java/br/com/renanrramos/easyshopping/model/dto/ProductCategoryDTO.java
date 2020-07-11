/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.ProductCategory;

/**
 * @author renan.ramos
 *
 */
public class ProductCategoryDTO {
	
	private Long id;

	private String name;
	
	public ProductCategoryDTO() {
	}

	public ProductCategoryDTO(ProductCategory productCategory) {
		this.id = productCategory.getId();
		this.name = productCategory.getName();
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
	
	public static List<ProductCategoryDTO> converterProductCategoryListToProductCategoryDTOList(List<ProductCategory> productCategory) {
		return productCategory.stream().map(ProductCategoryDTO::new).collect(Collectors.toList());
	}

	public static ProductCategoryDTO converterProductCategoryToProductCategoryDTO(ProductCategory productCategory) {
		return new ProductCategoryDTO(productCategory);
	}

	@Override
	public String toString() {
		return "ProductCategoryDTO [id=" + id + ", name=" + name + "]";
	}

}
