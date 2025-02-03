/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author renan.ramos
 */
@Data
@ToString
@NoArgsConstructor
public class Purchase {

    private Long id;
    private String customerId;
    private Order order;
    private Address address;
    private CreditCard creditCard;
    private LocalDateTime purchaseDate;

}
