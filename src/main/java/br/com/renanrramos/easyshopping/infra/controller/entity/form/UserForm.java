/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;

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
public class UserForm {

	private String name;

	private String email;

	private Profile profile;

	public UserForm() {
		// Intentionally empty
	}
}
