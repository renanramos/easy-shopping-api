/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import br.com.renanrramos.easyshopping.model.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
 *
 */
public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity, Long>{

	CompanyEntity findTopCompanyByRegisteredNumber(String registeredNumber);

	@Query(EasyShoppingSqlConstants.GET_COMPANY_BY_NAME)
	Page<CompanyEntity> getCompanyByNameRegisteredNumberOrEmail(Pageable page, String name);

	List<CompanyEntity> findAll();

	Optional<CompanyEntity> findCompanyByTokenId(String tokenId);
}