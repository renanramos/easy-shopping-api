/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.builder.StockItemBuilder;
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

	public StockItemForm(Long productId, Double maxAmount, Double minAmount, Integer currentAmount, Long stockId,
			String productName) {
		this.productId = productId;
		this.productName = productName;
		this.maxAmount = maxAmount;
		this.minAmount = minAmount;
		this.currentAmount = currentAmount;
		this.stockId = stockId;
	}

	public static StockItem converterStockItemFormToStockItem(StockItemForm itemForm) {
		return StockItemBuilder.builder().withCurrentAmount(itemForm.getCurrentAmount())
				.withMaxAmount(itemForm.getMaxAmount()).withMinAmount(itemForm.getMinAmount())
				.withProductId(itemForm.getProductId()).withProductName(itemForm.getProductName()).build();
	}

	public static StockItem converterStockItemFormUpdateToStockItem(StockItemForm itemForm,
			StockItem currentStockItem) {
		return StockItemBuilder.builder()
				.withCurrentAmount(
						Optional.ofNullable(itemForm.getCurrentAmount()).orElse(currentStockItem.getCurrentAmount()))
				.withMaxAmount(Optional.ofNullable(itemForm.getMaxAmount()).orElse(currentStockItem.getMaxAmount()))
				.withMinAmount(Optional.ofNullable(itemForm.getMinAmount()).orElse(currentStockItem.getMinAmount()))
				.withProductId(Optional.ofNullable(itemForm.getProductId()).orElse(currentStockItem.getProductId()))
				.withProductName(
						Optional.ofNullable(itemForm.getProductName()).orElse(currentStockItem.getProductName()))
				.build();
	}
}