/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDTO {
	private Long id;

	private String orderNumber;

	private String customerId;

	private boolean isFindished;

	public OrderDTO() {
		// Intentionally empty
	}

	public OrderDTO(Order order) {
		this.id = order.getId();
		this.orderNumber = order.getOrderNumber();
		this.customerId = order.getCustomerId();
		this.isFindished = order.isFinished();
	}

	public static List<OrderDTO> converterOrderListToOrderDTOList(List<Order> orders) {
		return orders.stream().map(OrderDTO::new).collect(Collectors.toList());
	}

	public static OrderDTO converterOrderToOrderDTO(Order order) {
		return new OrderDTO(order);
	}

	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", orderNumber=" + orderNumber + ", customerId=" + customerId + "]";
	}
}
