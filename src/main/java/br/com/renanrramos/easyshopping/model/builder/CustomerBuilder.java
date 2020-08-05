/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 25/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Customer;

/**
 * @author renan.ramos
 *
 */
public class CustomerBuilder {

	private Customer customer;

	public CustomerBuilder() {
		this.customer = new Customer();
	}

	public static CustomerBuilder builder() {
		return new CustomerBuilder();
	}
	
	public CustomerBuilder withCpf(String cpf) {
		this.customer.setCpf(cpf);
		return this;
	}
	
	public CustomerBuilder withEmail(String email) {
		this.customer.setEmail(email);
		return this;
	}
	
	public CustomerBuilder withName(String name) {
		this.customer.setName(name);
		return this;
	}

	public CustomerBuilder withPassword(String password) {
		this.customer.setPassword(password);
		return this;
	}

	public Customer build() {
		this.customer.setProfile(Profile.CUSTOMER);
		return this.customer;
	}
}
