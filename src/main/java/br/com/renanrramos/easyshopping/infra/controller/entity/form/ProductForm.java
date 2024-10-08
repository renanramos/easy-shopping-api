/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 03/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

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
public class ProductForm {

	private String name;

	private String description;

	private double price;

	private Long productSubCategoryId;

	private Long storeId;

	private String companyId;

	public ProductForm() {
		// Intentionally empty
	}
}
