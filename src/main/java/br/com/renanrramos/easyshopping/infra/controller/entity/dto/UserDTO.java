/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 22/10/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

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
public class UserDTO {

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String name;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String email;

	private boolean isSync;
}
