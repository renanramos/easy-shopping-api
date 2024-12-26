/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Stock;

/**
 * @author renan.ramos
 *
 */
public interface StockRepository extends PagingAndSortingRepository<Stock, Long> {

	@Query(EasyShoppingSqlConstants.GET_STOCKS_BY_NAME)
	Page<Stock> findStockByName(Pageable page, String name);
}
