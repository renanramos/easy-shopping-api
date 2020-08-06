/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>{

	Customer findTopCustomerByCpf(String cpf);

}
