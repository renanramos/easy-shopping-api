/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.CreditCard;

/**
 * @author renan.ramos
 *
 */
public interface CreditCardRepositoy extends PagingAndSortingRepository<CreditCard, Long>{
	
	List<CreditCard> findCreditCardByCustomerId(Long customerId);
}
