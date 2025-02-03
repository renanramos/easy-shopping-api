/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author renan.ramos
 */
@Data
@ToString
@NoArgsConstructor
public class ProductImage {

    private Long id;
    private String description;
    private byte[] picture;
    private Product product;
    private boolean isCoverImage;
	
}
