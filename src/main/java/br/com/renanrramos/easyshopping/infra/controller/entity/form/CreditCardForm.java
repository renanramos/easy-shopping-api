/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.core.domain.constants.ValidationConstantMessages;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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
public class CreditCardForm {

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String creditCardNumber;

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String ownerName;

    @NotNull(message = ValidationConstantMessages.EMPTY_FIELD)
    private String validDate;

    @NotNull(message = ValidationConstantMessages.EMPTY_FIELD)
    private Integer code;

}
