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

import java.util.ArrayList;
import java.util.List;

/**
 * @author renan.ramos
 */
@Data
@ToString
@NoArgsConstructor
public class Stock {

    private Long id;
    private String name;
    private Store store;
    private List<StockItem> items = new ArrayList<>();

}
