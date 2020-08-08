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

import br.com.renanrramos.easyshopping.model.SubCategory;
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
public class SubCategoryDTO {

	private String name;

	private Long productCategoryId;

	public SubCategoryDTO() {
		// Intentionally empty
	}

	public SubCategoryDTO(SubCategory subCategory) {
		this.name = subCategory.getName();
		this.productCategoryId = Optional.ofNullable(subCategory.getProductCategory().getId()).orElse(null);
	}

	public static List<SubCategoryDTO> convertSubCategoryListToSubCategoryDTOList(List<SubCategory> subCategories) {
		return subCategories.stream().map(SubCategoryDTO::new).collect(Collectors.toList());
	}

	public static SubCategoryDTO convertSubCategoryToSubCategoryDTO(SubCategory subCategory) {
		return new SubCategoryDTO(subCategory);
	}

	@Override
	public String toString() {
		return "SubCategoryDTO [name=" + name + ", productCategoryId=" + productCategoryId + "]";
	}
}
