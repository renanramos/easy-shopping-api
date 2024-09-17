/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import java.time.LocalDateTime;

/**
 * @author renan.ramos
 *
 */
public class Purchase {

	private Long id;
	private String customerId;
	private Order order;
	private Address address;
	private CreditCard creditCard;
	private LocalDateTime purchaseDate;

	public Purchase() {
		// Intentionally empty
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", customerId=" + customerId + ", order=" + order + ", address=" + address
				+ ", creditCard=" + creditCard + ", date=" + purchaseDate + "]";
	}
}
