/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.renanrramos.easyshopping.model.Address;

/**
 * @author renan.ramos
 *
 */
public interface AddressRepository extends JpaRepository<Address, Long>{

	@Query(" FROM Address a LEFT JOIN Customer c ON (c.id = a.customer) WHERE a.customer = :customerId ")
	public List<Address> getAddressCustomerId(Long customerId);
	
}
