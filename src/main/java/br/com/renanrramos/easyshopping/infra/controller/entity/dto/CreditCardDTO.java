/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import java.time.LocalDate;

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
public class CreditCardDTO {

	private Long id;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String creditCardNumber;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String ownerName;

	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	private LocalDate validDate;

	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	private Integer code;

	private String customerId;

}
