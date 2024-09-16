/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.renanrramos.easyshopping.infra.config.util.EasyShoppingUtils;
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
public class ProductImageDTO {

	@JsonIgnore
	private EasyShoppingUtils utils = new EasyShoppingUtils();
	
	private Long id;

	private String description;

	private byte[] picture;

	private Long productId;

	public ProductImageDTO() {
		// Intentionally empty
	}
}
