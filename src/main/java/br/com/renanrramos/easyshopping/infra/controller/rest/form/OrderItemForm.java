/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest.form;

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
public class OrderItemForm {

	private Long orderId;

	private Long productId;

	private String productName;

	private Integer amount;

	private Double price;

	private Double total;

	public OrderItemForm() {
		// Intentionally empty
	}
}
