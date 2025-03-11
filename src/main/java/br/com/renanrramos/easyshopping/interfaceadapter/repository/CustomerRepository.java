/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.CustomerEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, Long>,
		CrudRepository<CustomerEntity, Long> {

	List<CustomerEntity> findCustomerByCpf(final String cpf);

	CustomerEntity findCustomerByTokenId(final String tokenId);

	@Query(EasyShoppingSqlConstants.GET_CUSTOMER_BY_NAME)
	Page<CustomerEntity> getCustomerByNameCPFOrEmail(final Pageable page, final @Param("name")String name);
}
