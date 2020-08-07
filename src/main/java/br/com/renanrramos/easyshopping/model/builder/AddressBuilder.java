/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.Customer;

/**
 * @author renan.ramos
 *
 */
public class AddressBuilder {

	private Address address;

	public AddressBuilder() {
		this.address = new Address();
	}
	
	public static AddressBuilder builder() {
		return new AddressBuilder();
	}
	
	public AddressBuilder withStreetName(String streetName) {
		this.address.setStreetName(streetName);
		return this;
	}
	
	public AddressBuilder withDistrict(String district) {
		this.address.setDistrict(district);
		return this;
	}
	
	public AddressBuilder withNumber(Long number) {
		this.address.setNumber(number);
		return this;
	}
	
	public AddressBuilder withCep(String cep) {
		this.address.setCep(cep);
		return this;
	}
	
	public AddressBuilder withState(String state) {
		this.address.setState(state);
		return this;
	}
	
	public AddressBuilder withCustomer(Customer customer) {
		this.address.setCustomer(customer);
		return this;
	}

	public AddressBuilder withCity(String city) {
		this.address.setCity(city);
		return this;
	}

	public Address build() {
		return this.address;
	}
}
