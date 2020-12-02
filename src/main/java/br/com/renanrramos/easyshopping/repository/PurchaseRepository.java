/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Purchase;

/**
 * @author renan.ramos
 *
 */
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Long> {

	List<Purchase> findPurchaseByCustomerId(String customerId);
}
