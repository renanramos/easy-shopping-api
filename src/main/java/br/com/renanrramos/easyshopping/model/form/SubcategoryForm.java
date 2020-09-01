/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

import br.com.renanrramos.easyshopping.model.Subcategory;
import br.com.renanrramos.easyshopping.model.builder.SubcategoryBuilder;
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
public class SubcategoryForm {

	private String name;

	private Long productCategoryId;

	public SubcategoryForm() {
		// Intentionally empty
	}

	public SubcategoryForm(String name, Long productCategoryId) {
		this.name = name;
		this.productCategoryId = productCategoryId;
	}

	public static Subcategory convertSubcategoryFormToSubcategory(SubcategoryForm subcategoryForm) {
		return new SubcategoryBuilder()
				.builder()
				.withName(subcategoryForm.getName())
				.build();
	}

	public static Subcategory convertSubcategoryFormUpdateToSubcategory(SubcategoryForm subcategoryForm, Subcategory currentSubcategory) {
		return new SubcategoryBuilder()
				.builder()
				.withName(Optional.ofNullable(subcategoryForm.getName()).orElse(currentSubcategory.getName()))
				.build();
	}

	@Override
	public String toString() {
		return "SubcategoryForm [name=" + name + ", productCategoryId=" + productCategoryId + "]";
	}
}
