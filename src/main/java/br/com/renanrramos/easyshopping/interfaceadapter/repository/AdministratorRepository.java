/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.AdministratorEntity;

import javax.transaction.Transactional;

/**
 * @author renan.ramos
 *
 */
public interface AdministratorRepository extends PagingAndSortingRepository<AdministratorEntity, Long> {

	List<AdministratorEntity> findAdministratorByNameContains(final String name);

	@Transactional
	@Modifying
	@Query("DELETE FROM Administrator WHERE ID = :administratorId")
	void removeById(final Long administratorId);
}
