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
public class ProductCategoryDTO {
	
	private Long id;

	private String name;
	
	public ProductCategoryDTO() {
		// Intentionally empty
	}

	public ProductCategoryDTO(ProductCategory productCategory) {
		this.id = productCategory.getId();
		this.name = productCategory.getName();
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
