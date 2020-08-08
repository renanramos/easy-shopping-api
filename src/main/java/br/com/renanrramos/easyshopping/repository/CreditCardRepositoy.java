/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.CreditCard;

/**
 * @author renan.ramos
 *
 */
public interface CreditCardRepositoy extends PagingAndSortingRepository<CreditCard, Long>{
	
	Page<CreditCard> findCreditCardByCustomerId(Pageable page, Long customerId);
}
