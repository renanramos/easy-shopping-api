/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import java.util.List;

import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.Store;

/**
 * @author renan.ramos
 *
 */
public class StockBuilder {

	private Stock stock;

	public StockBuilder() {
		this.stock = new Stock();
	}

	public static StockBuilder builder() {
		return new StockBuilder();
	}

	public StockBuilder withName(String name) {
		this.stock.setName(name);
		return this;
	}

	public StockBuilder withStore(Store store) {
		this.stock.setStore(store);
		return this;
	}

	public StockBuilder withStockItem(List<StockItem> items) {
		this.stock.setItems(items);
		return this;
	}

	public Stock build() {
		return this.stock;
	}
}
