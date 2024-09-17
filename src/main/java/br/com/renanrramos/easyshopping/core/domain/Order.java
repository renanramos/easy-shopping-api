/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author renan.ramos
 *
 */
public class Order {

	private Long id;
	private String orderNumber;
	private String customerId;
	private boolean finished;
	private List<OrderItem> items = new ArrayList<>();
	private Purchase purchase;

	public Order() {
		// Intentionally empty
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + "]";
	}
}
