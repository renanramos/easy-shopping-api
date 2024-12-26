/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class StoreDTO {

	@ApiModelProperty(hidden = true)
	private Long id;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String name;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String registeredNumber;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String corporateName;

	private String companyId;

	private String companyName;

}
