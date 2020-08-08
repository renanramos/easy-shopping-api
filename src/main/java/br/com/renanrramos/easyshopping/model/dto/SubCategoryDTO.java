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

	public SubcategoryDTO(Subcategory subCategory) {
		this.name = subCategory.getName();
		this.productCategoryId = Optional.ofNullable(subCategory.getProductCategory().getId()).orElse(null);
	}

	public static List<SubcategoryDTO> convertSubCategoryListToSubCategoryDTOList(List<Subcategory> subCategories) {
		return subCategories.stream().map(SubcategoryDTO::new).collect(Collectors.toList());
	}

	public static SubcategoryDTO convertSubCategoryToSubCategoryDTO(Subcategory subCategory) {
		return new SubcategoryDTO(subCategory);
	}

	@Override
	public String toString() {
		return "SubcategoryDTO [name=" + name + ", productCategoryId=" + productCategoryId + "]";
	}
}
