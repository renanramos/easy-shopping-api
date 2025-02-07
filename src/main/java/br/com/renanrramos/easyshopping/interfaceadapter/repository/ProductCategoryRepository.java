/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author renan.ramos
 *
 */
public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategoryEntity, Long> {

    Page<ProductCategoryEntity> findProductCategoryByNameContaining(final Pageable page, final String name);
}
