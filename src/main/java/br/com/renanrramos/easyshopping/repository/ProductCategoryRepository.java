/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.ProductCategory;

/**
 * @author renan.ramos
 *
 */
public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory, Long>{

}
