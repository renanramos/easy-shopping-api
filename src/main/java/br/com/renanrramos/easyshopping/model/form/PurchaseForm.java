/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

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
public class PurchaseForm {

	private Long orderId;

	private Long addressId;

	private Long creditCardId;

	public PurchaseForm() {
		// Intentionally empty
	}

	public PurchaseForm(String customerId, Long orderId, Long addressId, Long creditCardId) {
		this.orderId = orderId;
		this.addressId = addressId;
		this.creditCardId = creditCardId;
	}
}
