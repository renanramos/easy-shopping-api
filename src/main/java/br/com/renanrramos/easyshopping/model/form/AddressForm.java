/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

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

	private String city;

	private Long customerId;

	public AddressForm() {
		// Intentionally empty
	}

	public AddressForm(String streetName, String district, Long number, String cep, String state, Long customerId, String city) {
		this.streetName = streetName;
		this.district = district;
		this.number = number;
		this.cep = cep;
		this.state = state;
		this.customerId = customerId;
		this.city = city;
	}

	public static Address converterAddressFormToAddress(AddressForm addressForm) {
		return AddressBuilder
				.builder()
				.withCep(addressForm.getCep())
				.withDistrict(addressForm.getDistrict())
				.withNumber(addressForm.getNumber())
				.withState(addressForm.getState())
				.withStreetName(addressForm.getStreetName())
				.withCity(addressForm.getCity())
				.build();
	}

	public static Address converterAddressFormUpdateToAddress(AddressForm addressForm, Address currentAddress) {
		return AddressBuilder
				.builder()
				.withCep(Optional.ofNullable(addressForm.getCep()).orElse(currentAddress.getCep()))
				.withDistrict(Optional.ofNullable(addressForm.getDistrict()).orElse(currentAddress.getDistrict()))
				.withNumber(Optional.ofNullable(addressForm.getNumber()).orElse(currentAddress.getNumber()))
				.withState(Optional.ofNullable(addressForm.getState()).orElse(currentAddress.getState()))
				.withStreetName(Optional.ofNullable(addressForm.getStreetName()).orElse(currentAddress.getStreetName()))
				.withCity(Optional.ofNullable(addressForm.getCity()).orElse(currentAddress.getCity()))
				.build();
	}

	@Override
	public String toString() {
		return "AddressForm [streetName=" + streetName + ", district=" + district + ", number=" + number + ", cep="
				+ cep + ", state=" + state + ", city=" + city + ", customerId=" + customerId + "]";
	}
}
