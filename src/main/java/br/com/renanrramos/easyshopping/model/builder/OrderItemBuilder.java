/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.OrderItem;

/**
 * @author renan.ramos
 *
 */
public class OrderItemBuilder {

	private OrderItem orderItem;

	public OrderItemBuilder() {
		this.orderItem = new OrderItem();
	}

	public static OrderItemBuilder builder() {
		return new OrderItemBuilder();
	}

	public OrderItemBuilder withOrder(Order order) {
		this.orderItem.setOrder(order);
		return this;
	}

	public OrderItemBuilder withProductId(Long productId) {
		this.orderItem.setProductId(productId);
		return this;
	}

	public OrderItemBuilder withAmount(Integer amount) {
		this.orderItem.setAmount(amount);
		return this;
	}

	public OrderItemBuilder withPrice(Double price) {
		this.orderItem.setPrice(price);
		return this;
	}

	public OrderItemBuilder withTotal(Double total) {
		this.orderItem.setTotal(total);
		return this;
	}

	public OrderItem build() {
		return this.orderItem;
	}
}
