/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 07/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
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
public class ProductCategoryDTO {

	@ApiModelProperty(hidden = true)
	private Long id;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String name;
}
