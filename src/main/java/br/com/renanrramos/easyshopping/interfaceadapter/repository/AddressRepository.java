/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import javax.transaction.Transactional;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author renan.ramos
 *
 */
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {

	Page<AddressEntity> findAddressByStreetNameContaining(Pageable page, String streetName);

	Page<AddressEntity> findAddressByCustomerId(Pageable page, String customerId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Address WHERE ID = :addressId")
	void removeById(Long addressId);
}
