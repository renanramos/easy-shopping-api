/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author renan.ramos
 *
 */
public class Store {

	private Long id;
	private String name;
	private String registeredNumber;
	private String corporateName;
	private List<Product> products = new ArrayList<>();
	private List<Stock> stocks = new ArrayList<>();
	private String tokenId;

	public Store() {
		// Intentionally empty
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

	public String getRegisteredNumber() {
		return registeredNumber;
	}

	public void setRegisteredNumber(String registeredNumber) {
		this.registeredNumber = registeredNumber;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", registeredNumber=" + registeredNumber + ", corporateName="
				+ corporateName + ", products=" + products + ", stocks=" + stocks + "]";
	}
}
