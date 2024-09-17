/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

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
public class ProductCategoryDTO {
	
	private Long id;

	private String name;

	public ProductCategoryDTO() {
		// Intentionally empty
	}
}
