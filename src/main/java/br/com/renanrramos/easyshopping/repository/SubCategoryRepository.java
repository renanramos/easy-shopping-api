/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.SubCategory;

/**
 * @author renan.ramos
 *
 */
public interface SubCategoryRepository extends PagingAndSortingRepository<SubCategory, Long> {

}
