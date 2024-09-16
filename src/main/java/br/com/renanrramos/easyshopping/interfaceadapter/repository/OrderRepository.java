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
import br.com.renanrramos.easyshopping.model.Order;

/**
 * @author renan.ramos
 *
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

	@Query(EasyShoppingSqlConstants.GET_CUSTOMER_ORDERS)
	List<Order> getCustomerOrders(String customerId);
}
