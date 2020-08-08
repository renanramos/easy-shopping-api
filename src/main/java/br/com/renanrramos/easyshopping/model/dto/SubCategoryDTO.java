/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Subcategory;
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
public class SubcategoryDTO {

	private String name;

	private Long productCategoryId;

	public SubcategoryDTO() {
		// Intentionally empty
	}

	public SubcategoryDTO(Subcategory subcategory) {
		this.name = subcategory.getName();
		this.productCategoryId = Optional.ofNullable(subcategory.getProductCategory().getId()).orElse(null);
	}

	public static List<SubcategoryDTO> convertSubcategoryListToSubcategoryDTOList(List<Subcategory> subcategories) {
		return subcategories.stream().map(SubcategoryDTO::new).collect(Collectors.toList());
	}

	public static SubcategoryDTO convertSubcategoryToSubcategoryDTO(Subcategory subcategory) {
		return new SubcategoryDTO(subcategory);
	}

	@Override
	public String toString() {
		return "SubcategoryDTO [name=" + name + ", productCategoryId=" + productCategoryId + "]";
	}
}
