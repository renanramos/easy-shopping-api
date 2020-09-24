/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.ProductImage;

/**
 * @author renan.ramos
 *
 */
public interface ProductImageRepository extends PagingAndSortingRepository<ProductImage, Long> {
}
