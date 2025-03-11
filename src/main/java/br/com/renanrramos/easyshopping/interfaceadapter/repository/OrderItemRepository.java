/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderItemEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author renan.ramos
 *
 */
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItemEntity, Long>, CrudRepository<OrderItemEntity, Long> {

    @Query(EasyShoppingSqlConstants.GET_ORDER_ITEMS_BY_ORDER_ID)
    List<OrderItemEntity> getOrderItemByOrderId(Long orderId);

    @Query(EasyShoppingSqlConstants.GET_ORDER_ITEM_STATISTIC)
    List<OrderItemEntity> getOrderItemStatistic();
}
