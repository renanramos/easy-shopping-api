/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.builder.OrderBuilder;
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
public class OrderForm {

	private Long id;

	private String orderNumber;

	private String customerId;

	public OrderForm() {
		// Intentionally empty
	}

	public OrderForm(String orderNumber, String customerId) {
		this.orderNumber = orderNumber;
		this.customerId = customerId;
	}

	public static Order converterOrderFormToOrder(OrderForm orderForm) {
		return OrderBuilder.builder().withCustomerId(orderForm.getCustomerId())
				.withOrderNumber(orderForm.getOrderNumber()).build();
	}

	public static Order converterOrderFormUpdateToOrder(OrderForm orderForm, Order order) {
		return OrderBuilder.builder()
				.withCustomerId(Optional.ofNullable(orderForm.getCustomerId()).orElse(order.getCustomerId()))
				.withOrderNumber(Optional.ofNullable(orderForm.getOrderNumber()).orElse(order.getOrderNumber()))
				.build();
	}

}
