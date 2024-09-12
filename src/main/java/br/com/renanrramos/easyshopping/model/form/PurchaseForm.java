/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseForm {

	private Long orderId;

	private Long addressId;

	private Long creditCardId;

	public PurchaseForm() {
		// Intentionally empty
	}
}
