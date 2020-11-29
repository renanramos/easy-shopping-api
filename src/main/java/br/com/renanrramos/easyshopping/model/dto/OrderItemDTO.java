/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

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

	private Long orderId;

	private Long productId;

	private Integer amount;

	private Double price;

	private Double total;

	private String productName;

	public OrderItemDTO() {
		// Intentionally empty
	}

	public OrderItemDTO(OrderItem orderItem) {
		this.id = orderItem.getId();
		this.orderId = orderItem.getOrder().getId();
		this.productId = orderItem.getProductId();
		this.productName = orderItem.getProductName();
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
		return "OrderItemDTO [id=" + id + ", orderId=" + orderId + ", productId=" + productId + ", amount=" + amount
				+ ", price=" + price + ", total=" + total + "]";
	}
}
