/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
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
public class AddressForm {

	private String streetName;

	private String district;

	private Long number;

	private String cep;

	private String state;

	private String city;

	public AddressForm() {
		// Intentionally empty
	}
}
