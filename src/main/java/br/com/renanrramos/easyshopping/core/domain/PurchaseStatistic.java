package br.com.renanrramos.easyshopping.core.domain;

public class PurchaseStatistic {

	private Order order;

	private Purchase purchase;

	public PurchaseStatistic() {
		// Intentionally empty
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	@Override
	public String toString() {
		return "PurchaseStatistic{" +
				"order=" + order +
				", purchase=" + purchase +
				'}';
	}
}
