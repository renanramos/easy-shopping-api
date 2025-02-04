/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

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

    private String creditCardNumber;

    private String ownerName;

    private LocalDate validDate;

    private Integer code;

    private String customerId;

}
