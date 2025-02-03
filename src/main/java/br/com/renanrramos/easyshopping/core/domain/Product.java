/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @author renan.ramos
 */
@Data
@ToString
@NoArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String description;
    private double price;
    private SubCategory subCategory;
    private Store store;
    private String companyId;
    private Set<ProductImage> images;
    private boolean isPublished;

}
