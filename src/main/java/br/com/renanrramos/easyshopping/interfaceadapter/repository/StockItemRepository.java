/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.StockItem;

/**
 * @author renan.ramos
 *
 */
public interface StockItemRepository extends PagingAndSortingRepository<StockItem, Long> {

	Page<StockItem> findStockItemByStockId(Pageable page, Long stockId);

	@Query(EasyShoppingSqlConstants.GET_STOCK_ITEM_BY_PRODUCT_NAME)
	Page<StockItem> findStockItemByStockIdAndName(Pageable page, Long stockId, String name);

	@Query(EasyShoppingSqlConstants.GET_STOCK_ITEM_LEFT_JOIN_PRODUCT)
	List<StockItem> getStockItemWithProductInfo();

	Optional<StockItem> findTopStockItemByProductId(Long productId);
}
