/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.StockItem;

/**
 * @author renan.ramos
 *
 */
public interface StockItemRepository extends PagingAndSortingRepository<StockItem, Long> {
	Page<StockItem> findStockItemByStockId(Pageable page, Long stockId);
}
