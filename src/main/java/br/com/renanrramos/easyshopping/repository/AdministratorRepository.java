/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.renanrramos.easyshopping.model.Administrator;

/**
 * @author renan.ramos
 *
 */
public interface AdministratorRepository extends CrudRepository<Administrator, Long> {

}
