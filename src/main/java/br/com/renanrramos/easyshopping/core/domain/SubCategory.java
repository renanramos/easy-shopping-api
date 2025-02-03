/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 08/08/2020
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
public class SubCategory {

    private Long id;
    private String name;
    private ProductCategory productCategory;
    private List<Product> products = new ArrayList<>();

}
