/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.Stock;

/**
 * @author renan.ramos
 *
 */
public interface StockRepository extends PagingAndSortingRepository<Stock, Long> {

	Page<Stock> findStockByName(String name);
}
