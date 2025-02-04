/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 03/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.core.domain.constants.ValidationConstantMessages;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class ProductForm {

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String name;

    @NotBlank(message = ValidationConstantMessages.EMPTY_FIELD)
    private String description;

    private double price;

    private Long productSubCategoryId;

    private Long storeId;

    private String companyId;

}
