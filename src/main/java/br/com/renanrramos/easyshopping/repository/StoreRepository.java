/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Store;

/**
 * @author renan.ramos
 *
 */
public interface StoreRepository extends PagingAndSortingRepository<Store, Long>{

	Page<Store> findStoreByCompanyId(Pageable page, Long companyId);

	Page<Store> findStoreByNameContaining(Pageable page, String name);
}
