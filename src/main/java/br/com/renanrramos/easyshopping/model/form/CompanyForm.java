/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 30/06/2020
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
public class CompanyForm {

	private String email;

	private String name;

	private String registeredNumber;

	private String phone;

	public CompanyForm() {
		// Intentionally empty
	}
}
