/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.core.domain.constants.ValidationConstantMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class AddressForm {

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String streetName;

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String district;

    @NotNull(message = ValidationConstantMessages.EMPTY_FIELD)
    private Long number;

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String cep;

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String state;

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String city;

}
