/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import java.util.List;

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.OrderItem;

/**
 * @author renan.ramos
 *
 */
public class OrderBuilder {

	private Order order;

	public OrderBuilder() {
		this.order = new Order();
	}

	public static OrderBuilder builder() {
		return new OrderBuilder();
	}

	public OrderBuilder withOrderNumber(String orderNumber) {
		this.order.setOrderNumber(orderNumber);
		return this;
	}

	public OrderBuilder withCustomerId(String customerId) {
		this.order.setCustomerId(customerId);
		return this;
	}

	public OrderBuilder withOrderItems(List<OrderItem> orderItems) {
		this.order.setItems(orderItems);
		return this;
	}

	public Order build() {
		return this.order;
	}
}
