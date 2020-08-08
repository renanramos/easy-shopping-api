/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Administrator;

/**
 * @author renan.ramos
 *
 */
public interface AdministratorRepository extends PagingAndSortingRepository<Administrator, Long> {

	List<Administrator> findAdministratorByNameContains(String name);
}
