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

/**
 * @author renan.ramos
 *
 */
public class AddressDTO {

	private Long id;
	
	private String streetName;

	private String district;

	private Long number;

	private String cep;

	private String state;
	
	private Long customerId;
	
	public AddressDTO() {
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

	public Long getId() {
		return id;
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

	public void setId(Long id) {
		this.id = id;
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
