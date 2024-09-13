/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
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
public class StockItemForm {

	private Long productId;

	private String productName;

	private Double maxAmount;

	private Double minAmount;

	private Integer currentAmount;

	private Long stockId;

	public StockItemForm() {
		// Intentionally empty
	}
}