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

import java.util.ArrayList;
import java.util.List;

/**
 * @author renan.ramos
 */
@Data
@ToString
@NoArgsConstructor
public class Order {

    private Long id;
    private String orderNumber;
    private String customerId;
    private boolean finished;
    private List<OrderItem> items = new ArrayList<>();
    private Purchase purchase;
}
