/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Address;

/**
 * @author renan.ramos
 *
 */
public interface AddressRepository extends PagingAndSortingRepository<Address, Long>{
	
	Page<Address> findAddressByCustomerId(Pageable page, Long customerId);
}
