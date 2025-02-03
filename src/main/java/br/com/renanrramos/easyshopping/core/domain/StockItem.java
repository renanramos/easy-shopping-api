/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 10/11/2020
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
public class StockItem {

    private Long id;
    private Stock stock;
    private Long productId;
    private String productName;
    private Double maxAmount;
    private Double minAmount;
    private Integer currentAmount;

}
