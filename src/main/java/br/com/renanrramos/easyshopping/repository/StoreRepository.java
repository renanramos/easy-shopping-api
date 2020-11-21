/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.renanrramos.easyshopping.constants.sql.EasyShoppingSqlConstants;
import br.com.renanrramos.easyshopping.model.Store;

/**
 * @author renan.ramos
 *
 */
public interface StoreRepository extends PagingAndSortingRepository<Store, Long>{

	@Query(EasyShoppingSqlConstants.GET_STORE_BY_NAME)
	Page<Store> findStoreByTokenId(Pageable page, @Param("tokenId") String tokenId, @Param("name") String name);

	@Query(EasyShoppingSqlConstants.GET_STORE_WITHOUT_TOKEN_ID)
	Page<Store> getStoreByNameCorporateNameRegisteredNumberOrCompanyName(Pageable page, @Param("name") String name);

	Optional<Store> findTopStoreByRegisteredNumber(String registeredNumber);
}