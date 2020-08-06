/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanrramos.easyshopping.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Customer findTopCustomerByCpf(String cpf);
}
