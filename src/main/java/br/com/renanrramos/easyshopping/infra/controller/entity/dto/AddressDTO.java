/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AddressDTO {

    private Long id;

    private String streetName;

    private String district;

    private Long number;

    private String cep;

    private String state;

    private String city;

    private String customerId;
}
