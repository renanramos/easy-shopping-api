/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Product;

/**
 * @author renan.ramos
 *
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

	List<Product> findProductByStoreId(Long storeId);
}
