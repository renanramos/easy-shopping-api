/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Store;

/**
 * @author renan.ramos
 *
 */
public interface StoreRepository extends PagingAndSortingRepository<Store, Long>{

	List<Store> findStoreByCompanyId(Long companyId);
}
