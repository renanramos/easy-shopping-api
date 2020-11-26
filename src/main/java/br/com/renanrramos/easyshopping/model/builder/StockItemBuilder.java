/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.model.StockItem;

/**
 * @author renan.ramos
 *
 */
public class StockItemBuilder {

	private StockItem stockItem;

	public StockItemBuilder() {
		this.stockItem = new StockItem();
	}

	public static StockItemBuilder builder() {
		return new StockItemBuilder();
	}

	public StockItemBuilder withId(Long id) {
		this.stockItem.setId(id);
		return this;
	}

	public StockItemBuilder withStock(Stock stock) {
		this.stockItem.setStock(stock);
		return this;
	}

	public StockItemBuilder withProductId(Long productId) {
		this.stockItem.setProductId(productId);
		return this;
	}

	public StockItemBuilder withMaxAmount(Double maxAmount) {
		this.stockItem.setMaxAmount(maxAmount);
		return this;
	}

	public StockItemBuilder withMinAmount(Double minAmount) {
		this.stockItem.setMinAmount(minAmount);
		return this;
	}

	public StockItemBuilder withCurrentAmount(Double currentAmount) {
		this.stockItem.setCurrentAmount(currentAmount);
		return this;
	}

	public StockItemBuilder withProductName(String productName) {
		this.stockItem.setProductName(productName);
		return this;
	}

	public StockItem build() {
		return this.stockItem;
	}
}
