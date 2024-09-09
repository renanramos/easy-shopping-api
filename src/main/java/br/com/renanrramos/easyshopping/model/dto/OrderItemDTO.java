/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

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
public class OrderItemDTO {

	private Long id;

	private Long orderId;

	private Long productId;

	private Integer amount;

	private Double price;

	private Double total;

	private String productName;

	public OrderItemDTO() {
		// Intentionally empty
	}
}
