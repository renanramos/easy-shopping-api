/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class AddressForm {

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String streetName;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String district;

	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	private Long number;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String cep;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String state;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String city;

}
