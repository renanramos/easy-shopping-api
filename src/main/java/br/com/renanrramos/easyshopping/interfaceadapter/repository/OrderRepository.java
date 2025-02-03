/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author renan.ramos
 */
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {

    @Query(EasyShoppingSqlConstants.GET_CUSTOMER_ORDERS)
    Page<OrderEntity> getCustomerOrders(String customerId);

    Page<OrderEntity> findOrderByOrderNumber(final String orderNumber);
}
