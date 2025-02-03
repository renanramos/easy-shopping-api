/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 26/06/2020
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
 *
 */
@Data
@ToString
@NoArgsConstructor
public class Store {

    private Long id;
    private String name;
    private String registeredNumber;
    private String corporateName;
    private List<Product> products = new ArrayList<>();
    private List<Stock> stocks = new ArrayList<>();
    private String tokenId;

}
