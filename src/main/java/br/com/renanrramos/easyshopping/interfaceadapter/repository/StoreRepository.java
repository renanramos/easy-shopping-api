/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.Store;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author renan.ramos
 *
 */
public interface StoreRepository extends PagingAndSortingRepository<Store, Long>, CrudRepository<Store, Long> {

	@Query(EasyShoppingSqlConstants.GET_STORE_BY_NAME)
	Page<Store> findStoreByTokenId(Pageable page, @Param("tokenId") String tokenId, @Param("name") String name);

	@Query(EasyShoppingSqlConstants.GET_STORE_WITHOUT_TOKEN_ID)
	Page<Store> getStoreByNameCorporateNameRegisteredNumberOrCompanyName(Pageable page, @Param("name") String name);

	Optional<Store> findTopStoreByRegisteredNumber(String registeredNumber);
}