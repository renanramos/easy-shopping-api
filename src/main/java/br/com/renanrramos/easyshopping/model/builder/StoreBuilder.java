/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import java.util.Set;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.Store;

/**
 * @author renan.ramos
 *
 */
public class StoreBuilder {

	private Store store;
	
	private StoreBuilder() {
		this.store = new Store();
	}
	
	public static StoreBuilder builder() {
		return new StoreBuilder();
	}
	
	public StoreBuilder withName(String name) {
		this.store.setName(name);
		return this;
	}
	
	public StoreBuilder withRegisteredNumber(String registeredNumber) {
		this.store.setRegisteredNumber(registeredNumber);
		return this;
	}
	
	public StoreBuilder withCorporateName(String corporateName) {
		this.store.setCorporateName(corporateName);
		return this;
	}
	
	public StoreBuilder withProducts(Set<Product> products) {
		this.store.setProducts(products);
		return this;
	}
	
	public Store build() {
		return this.store;
	}
	
}
