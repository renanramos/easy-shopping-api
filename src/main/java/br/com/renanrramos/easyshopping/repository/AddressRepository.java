/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Address;

/**
 * @author renan.ramos
 *
 */
public interface AddressRepository extends PagingAndSortingRepository<Address, Long>{
	
	List<Address> findAddressByCustomerId(Long customerId);
}
