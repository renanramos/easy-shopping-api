/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Company;

/**
 * @author renan.ramos
 *
 */
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long>{

	Company findTopCompanyByRegisteredNumber(String registeredNumber);

	Page<Company> findCompanyByNameContaining(Pageable page, String name);

	List<Company> findAll();
}
