/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.builder.AddressBuilder;

/**
 * @author renan.ramos
 *
 */
public class AddressForm {

	private String streetName;

	private String district;

	private Long number;

	private String cep;

	private String state;
	
	private Long customerId;

	public AddressForm() {
	}

	public AddressForm(String streetName, String district, Long number, String cep, String state, Long customerId) {
		this.streetName = streetName;
		this.district = district;
		this.number = number;
		this.cep = cep;
		this.state = state;
		this.customerId = customerId;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getDistrict() {
		return district;
	}

	public Long getNumber() {
		return number;
	}

	public String getCep() {
		return cep;
	}

	public String getState() {
		return state;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	

	public static Address convertAddressFormToAddress(AddressForm addressForm) {
		return AddressBuilder
				.builder()
				.withCep(addressForm.cep)
				.withDistrict(addressForm.getDistrict())
				.withNumber(addressForm.getNumber())
				.withState(addressForm.getState())
				.withStreetName(addressForm.getStreetName())
				.build();
	}
	
}
