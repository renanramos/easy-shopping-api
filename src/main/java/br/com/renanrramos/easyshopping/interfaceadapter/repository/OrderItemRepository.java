/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderItem;

/**
 * @author renan.ramos
 *
 */
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {

	@Query(EasyShoppingSqlConstants.GET_ORDER_ITEMS_BY_ORDER_ID)
	List<OrderItem> getOrderItemByOrderId(Long orderId);

	@Query(EasyShoppingSqlConstants.GET_ORDER_ITEM_STATISTIC)
	List<OrderItem> getOrderItemStatistic();
}
