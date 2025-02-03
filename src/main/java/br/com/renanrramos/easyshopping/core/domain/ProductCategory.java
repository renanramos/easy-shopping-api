/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@NoArgsConstructor
public class ProductCategory {

    private Long id;
    private String name;
    private List<SubCategory> subcategories;

}
