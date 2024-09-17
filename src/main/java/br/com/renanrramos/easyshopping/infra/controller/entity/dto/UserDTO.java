/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 22/10/2020
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
public class UserDTO {

	private String name;

	private String email;

	private boolean isSync;

	public UserDTO() {
		// Intentionally empty
	}
}
