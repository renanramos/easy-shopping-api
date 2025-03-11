/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.core.domain.constants.ValidationConstantMessages;
import jakarta.validation.constraints.NotBlank;
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
public class StoreForm {

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String name;

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String registeredNumber;

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String corporateName;

    private String companyId;

}
