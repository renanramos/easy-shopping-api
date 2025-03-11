/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.AdministratorEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author renan.ramos
 *
 */
public interface AdministratorRepository extends PagingAndSortingRepository<AdministratorEntity, Long>, CrudRepository<AdministratorEntity, Long> {

	Page<AdministratorEntity> findAdministratorByNameContains(final Pageable pageable, final String name);

	@Transactional
	@Modifying
	@Query("DELETE FROM Administrator WHERE ID = :administratorId")
	void removeById(final Long administratorId);
}
