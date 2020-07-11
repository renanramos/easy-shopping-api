/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Customer;

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

	public CustomerDTO() {
	}
	
	public CustomerDTO(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.cpf = customer.getCpf();
		this.email = customer.getEmail();
		this.profile = customer.getProfile();
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

}
