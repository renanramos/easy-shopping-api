/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.CreditCard;

/**
 * @author renan.ramos
 *
 */
public interface CreditCardRepositoy extends PagingAndSortingRepository<CreditCard, Long>{
	
	Page<CreditCard> findCreditCardByCustomerId(Pageable page, String customerId);

	@Transactional
	@Modifying
	@Query("DELETE FROM CreditCard WHERE ID = :creditCardId")
	public void removeById(Long creditCardId);
}
