/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 08/08/2020
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
public class SubCategoryForm {

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String name;

	private Long productCategoryId;

}
