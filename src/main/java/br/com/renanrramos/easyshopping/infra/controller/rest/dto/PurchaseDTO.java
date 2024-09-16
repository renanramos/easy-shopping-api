/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest.dto;

import java.time.LocalDateTime;

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
public class PurchaseDTO {

	private Long id;

	private String customerId;

	private Long orderId;

	private Long addressId;

	private Long creditCardId;

	private LocalDateTime date;

	public PurchaseDTO() {
		// Intentionally empty
	}
}
