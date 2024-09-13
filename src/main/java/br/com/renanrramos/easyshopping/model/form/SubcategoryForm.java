/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SubcategoryForm {

	private String name;

	private Long productCategoryId;

	public SubcategoryForm() {
		// Intentionally empty
	}
}
