/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author renan.ramos
 *
 */
public class Stock {

	private Long id;
	private String name;
	private Store store;
	private List<StockItem> items = new ArrayList<>();

	public Stock() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<StockItem> getItems() {
		return items;
	}

	public void setItems(List<StockItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", name=" + name + ", store=" + store.getName() + "]";
	}
}
