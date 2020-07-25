/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.renanrramos.easyshopping.model.Address;

/**
 * @author renan.ramos
 *
 */
public interface AddressRepository extends JpaRepository<Address, Long>{	
}
