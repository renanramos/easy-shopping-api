/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Address;
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
public class AddressDTO {

	private Long id;
	
	private String streetName;

	private String district;

	private Long number;

	private String cep;

	private String state;
	
	private Long customerId;
	
	public AddressDTO() {
		// Intentionally empty
	}
	
	public AddressDTO(Address address) {
		this.id = address.getId();
		this.streetName = address.getStreetName();
		this.district = address.getDistrict();
		this.number = address.getNumber();
		this.cep = address.getCep();
		this.state = address.getState();
		this.customerId = address.getCustomer().getId();
				
	}

	public static List<AddressDTO> convertAddressListToAddressDTOList(List<Address> addresses) {
		return addresses.stream().map(AddressDTO::new).collect(Collectors.toList());
	}
	
	public static AddressDTO convertAddressToAddressDTO(Address address) {
		return new AddressDTO(address);
	}

	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", streetName=" + streetName + ", district=" + district + ", number=" + number
				+ ", cep=" + cep + ", state=" + state + ", customerId=" + customerId + "]";
	}

}
