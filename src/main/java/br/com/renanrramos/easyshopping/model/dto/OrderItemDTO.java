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
public class OrderItemDTO {

	private Long id;

	private Order order;

	private Long productId;

	private Integer amount;

	private Double price;

	private Double total;

	public OrderItemDTO() {
		// Intentionally empty
	}

	public OrderItemDTO(OrderItem orderItem) {
		this.id = orderItem.getId();
		this.order = orderItem.getOrder();
		this.productId = orderItem.getProductId();
		this.amount = orderItem.getAmount();
		this.price = orderItem.getPrice();
		this.total = orderItem.getTotal();
	}

	public static List<OrderItemDTO> converterOrderItemListToOrderItemDTOList(List<OrderItem> orderItems) {
		return orderItems.stream().map(OrderItemDTO::new).collect(Collectors.toList());
	}

	public static OrderItemDTO converterOrderItemToOrderItemDTO(OrderItem orderItem) {
		return new OrderItemDTO(orderItem);
	}

	@Override
	public String toString() {
		return "OrderItemDTO [id=" + id + ", order=" + order + ", productId=" + productId + ", amount=" + amount
				+ ", price=" + price + ", total=" + total + "]";
	}
}
