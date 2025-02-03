/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@NoArgsConstructor
public class Address {

    private Long id;
    private String streetName;
    private String district;
    private Long number;
    private String cep;
    private String state;
    private String city;
    private String customerId;
    private Purchase purchase;

}
