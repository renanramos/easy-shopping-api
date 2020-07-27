/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.builder.AddressBuilder;

/**
 * @author renan.ramos
 *
 */
public class CustomerDTO {
	
	private Long id;

	private String name;
	
	private String cpf;
	
	private String email;

	private Profile profile;
	
	private Set<Address> address;

	public CustomerDTO() {
	}
	
	public CustomerDTO(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.cpf = customer.getCpf();
		this.email = customer.getEmail();
		this.profile = customer.getProfile();
		this.address = customer.getAddress() != null ?
				customer.getAddress().stream().map(address -> getAddressInfo(address)).collect(Collectors.toSet()) :
					new HashSet<Address>();
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

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Profile getProfile() {
		return profile;
	}
	
	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	public static List<CustomerDTO> converterCustomerListToCustomerDTOList(List<Customer> customer) {
		return customer.stream().map(CustomerDTO::new).collect(Collectors.toList()); 
	}
	
	public static CustomerDTO converterToCustomerDTO(Customer customer) {
		return new CustomerDTO(customer);
	}

	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", name=" + name + ", cpf=" + cpf + ", email=" + email + ", profile=" + profile
				+ "]";
	}

	private static Address getAddressInfo(Address address) {
		return AddressBuilder.builder()
				.withCep(address.getCep())
				.withDistrict(address.getDistrict())
				.withNumber(address.getNumber())
				.withState(address.getState())
				.withStreetName(address.getStreetName())
				.build();
	}
}
