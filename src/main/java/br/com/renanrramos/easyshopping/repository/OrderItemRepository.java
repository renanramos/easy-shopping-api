/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.renanrramos.easyshopping.model.OrderItem;

/**
 * @author renan.ramos
 *
 */
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {

}
