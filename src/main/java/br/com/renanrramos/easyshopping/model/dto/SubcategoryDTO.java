/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.ToString;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SubcategoryDTO {

	private Long id;

	private String name;

	private Long productCategoryId;

	private String productCategoryName;

	public SubcategoryDTO() {
		// Intentionally empty
	}
}
