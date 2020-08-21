/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Product;

/**
 * @author renan.ramos
 *
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

	Page<Product> findProductByStoreId(Pageable page, Long storeId);

	Page<Product> findProductByNameContaining(Pageable page, String name);

	Optional<List<Product>> findProductByProductCategoryId(Long productCategoryId);

	Page<Product> findProductByCompanyId(Pageable page, Long companyId);
}
