/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.model.builder.OrderItemBuilder;
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
public class OrderItemForm {

	private Long orderId;

	private Long productId;

	private Double amount;

	private Double price;

	private Double total;

	public OrderItemForm() {
		// Intentionally empty
	}

	public OrderItemForm(Long orderId, Long productId, Double amount, Double price, Double total) {
		this.orderId = orderId;
		this.productId = productId;
		this.amount = amount;
		this.price = price;
		this.total = total;
	}

	public static OrderItem converterOrderItemFormToOrderItem(OrderItemForm orderItemForm) {
		return OrderItemBuilder.builder().withProductId(orderItemForm.getProductId())
				.withAmount(orderItemForm.getAmount()).withPrice(orderItemForm.getPrice())
				.withTotal(orderItemForm.getTotal()).build();
	}

	public static OrderItem converterOrderItemFormUpdateToOrderItem(OrderItemForm orderItemForm, OrderItem orderItem) {
		return OrderItemBuilder.builder()
				.withProductId(Optional.ofNullable(orderItemForm.getProductId()).orElse(orderItem.getProductId()))
				.withAmount(Optional.ofNullable(orderItemForm.getAmount()).orElse(orderItem.getAmount()))
				.withPrice(Optional.ofNullable(orderItemForm.getPrice()).orElse(orderItem.getPrice()))
				.withTotal(Optional.ofNullable(orderItemForm.getTotal()).orElse(orderItem.getTotal())).build();
	}
}
