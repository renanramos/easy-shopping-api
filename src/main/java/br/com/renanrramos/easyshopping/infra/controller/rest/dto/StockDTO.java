/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest.dto;

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
public class StockDTO {

	private Long id;

	private String name;

	private Long storeId;

	private String storeName;

	private int totalItems;

	public StockDTO() {
		// Intentionally empty
	}
}