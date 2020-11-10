/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.model.builder.StockBuilder;
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
public class StockForm {
	private String name;

	private Long storeId;

	private String storeName;

	public StockForm(String name, Long storeId, String storeName) {
		this.name = name;
		this.storeId = storeId;
		this.storeName = storeName;
	}

	public static Stock converterStockFormToStock(StockForm stockForm) {
		return StockBuilder.builder().withName(stockForm.getName()).build();
	}

	public static Stock converterStockFormUpdateToStock(StockForm stockForm, Stock currentStock) {
		return StockBuilder.builder().withName(Optional.ofNullable(stockForm.getName()).orElse(currentStock.getName()))
				.build();
	}
}
