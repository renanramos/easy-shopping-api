/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
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
public class ProductImageForm {

	private String description;

	private Long productId;

	private boolean isCoverImage;

	private byte[] picture;

	public ProductImageForm() {
		// Intentionally empty
	}
}
