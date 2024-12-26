/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import javax.transaction.Transactional;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.CreditCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author renan.ramos
 *
 */
public interface CreditCardRepositoy extends PagingAndSortingRepository<CreditCardEntity, Long>{
	
	Page<CreditCardEntity> findCreditCardByCustomerId(Pageable page, String customerId);

	@Transactional
	@Modifying
	@Query("DELETE FROM CreditCardEntity WHERE ID = :creditCardId")
	void removeById(Long creditCardId);
}
