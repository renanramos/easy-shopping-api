/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.model.SubCategory;
import br.com.renanrramos.easyshopping.model.builder.SubCategoryBuilder;
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
public class SubCategoryForm {

	private String name;

	private Long productCategoryId;

	public SubCategoryForm() {
		// Intentionally empty
	}

	public SubCategoryForm(String name, Long productCategoryId) {
		this.name = name;
		this.productCategoryId = productCategoryId;
	}

	public SubCategory convertSubCategoryFormToSubCategory(SubCategoryForm subCategoryForm) {
		return new SubCategoryBuilder()
				.builder()
				.withName(subCategoryForm.getName())
				.build();
	}

	@Override
	public String toString() {
		return "SubCategoryForm [name=" + name + ", productCategoryId=" + productCategoryId + "]";
	}
}
