/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import io.swagger.annotations.ApiModelProperty;
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
public class StoreDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String name;

    private String registeredNumber;

    private String corporateName;

    private String companyId;

    private String companyName;

}
