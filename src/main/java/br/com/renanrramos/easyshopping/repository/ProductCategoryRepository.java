/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanrramos.easyshopping.model.ProductCategory;

/**
 * @author renan.ramos
 *
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{

}
