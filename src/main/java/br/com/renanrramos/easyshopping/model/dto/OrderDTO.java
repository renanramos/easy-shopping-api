/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.OrderItem;
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

	private List<OrderItem> items;

	public OrderDTO() {
		// Intentionally empty
	}

	public OrderDTO(Order order) {
		this.id = order.getId();
		this.orderNumber = order.getOrderNumber();
		this.customerId = order.getCustomerId();
		this.items = Optional.ofNullable(order.getItems()).orElse(new ArrayList<>());
	}

	public static List<OrderDTO> converterOrderListToOrderDTOList(List<Order> orders) {
		return orders.stream().map(OrderDTO::new).collect(Collectors.toList());
	}

	public static OrderDTO converterOrderToOrderDTO(Order order) {
		return new OrderDTO(order);
	}

	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", orderNumber=" + orderNumber + ", customerId=" + customerId + ", items=" + items
				+ "]";
	}
}
