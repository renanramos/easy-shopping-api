/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.builder.AddressBuilder;
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
public class AddressForm {

	private String streetName;

	private String district;

	private Long number;

	private String cep;

	private String state;
	
	private Long customerId;

	public AddressForm() {
		// Intentionally empty
	}

	public AddressForm(String streetName, String district, Long number, String cep, String state, Long customerId) {
		this.streetName = streetName;
		this.district = district;
		this.number = number;
		this.cep = cep;
		this.state = state;
		this.customerId = customerId;
	}

	public static Address converterAddressFormToAddress(AddressForm addressForm) {
		return AddressBuilder
				.builder()
				.withCep(addressForm.getCep())
				.withDistrict(addressForm.getDistrict())
				.withNumber(addressForm.getNumber())
				.withState(addressForm.getState())
				.withStreetName(addressForm.getStreetName())
				.build();
	}

	@Override
	public String toString() {
		return "AddressForm [streetName=" + streetName + ", district=" + district + ", number=" + number + ", cep="
				+ cep + ", state=" + state + ", customerId=" + customerId + "]";
	}
}
