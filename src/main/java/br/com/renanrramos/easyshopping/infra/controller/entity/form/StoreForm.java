/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class StoreForm {

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String name;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String registeredNumber;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String corporateName;
	
	private String companyId;

}
