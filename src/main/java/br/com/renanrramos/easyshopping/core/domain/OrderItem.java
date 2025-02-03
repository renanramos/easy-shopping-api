/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 23/11/2020
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
public class OrderItem {

    private Long id;
    private Order order;
    private Long productId;
    private String productName;
    private Integer amount;
    private Double price;
    private Double total;

}
