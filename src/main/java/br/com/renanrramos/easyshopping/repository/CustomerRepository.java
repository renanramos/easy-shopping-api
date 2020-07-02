/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.renanrramos.easyshopping.constants.sql.EasyShoppingConstants;
import br.com.renanrramos.easyshopping.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	@Query(EasyShoppingConstants.GET_CUSTOMER_BY_ID)
	List<Customer> getCustomerById(@Param("customerId") Long customerId);
}
