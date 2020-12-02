/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.Purchase;

/**
 * @author renan.ramos
 *
 */
public class PurchaseBuilder {

	private Purchase purchase;

	public PurchaseBuilder() {
		this.purchase = new Purchase();
	}

	public static PurchaseBuilder builder() {
		return new PurchaseBuilder();
	}

	public PurchaseBuilder withId(Long id) {
		this.purchase.setId(id);
		return this;
	}

	public PurchaseBuilder withCustomerId(String customerId) {
		this.purchase.setCustomerId(customerId);
		return this;
	}

	public PurchaseBuilder withOrder(Order order) {
		this.purchase.setOrder(order);
		return this;
	}

	public PurchaseBuilder withAddress(Address address) {
		this.purchase.setAddress(address);
		return this;
	}

	public PurchaseBuilder withCreditCard(CreditCard creditCard) {
		this.purchase.setCreditCard(creditCard);
		return this;
	}

	public Purchase build() {
		return this.purchase;
	}
}
