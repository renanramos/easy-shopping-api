/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@NoArgsConstructor
public class CreditCard {
    private Long id;
    private String creditCardNumber;
    private String ownerName;
    private LocalDate validDate;
    private Integer code;
    private String customerId;
    private Purchase purchase;

}
