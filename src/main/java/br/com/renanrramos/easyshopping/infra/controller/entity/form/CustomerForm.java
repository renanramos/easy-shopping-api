/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import io.swagger.annotations.ApiModelProperty;

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
public class CustomerForm {

	private String name;

	private String email;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String cpf;
	
	@ApiModelProperty(hidden = true)
	private Profile profile;

	private String password;

}
