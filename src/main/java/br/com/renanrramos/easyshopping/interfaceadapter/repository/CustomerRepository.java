/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>{

	List<Customer> findCustomerByCpf(String cpf);

	Optional<Customer> findCustomerByTokenId(String tokenId);

	@Query(EasyShoppingSqlConstants.GET_CUSTOMER_BY_NAME)
	Page<Customer> getCustomerByNameCPFOrEmail(Pageable page, @Param("name")String name);
}
