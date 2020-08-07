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
public class CustomerDTO {
	
	private Long id;

	private String name;
	
	private String cpf;
	
	private String email;

	private Profile profile;

	public CustomerDTO() {
		// Intentionally empty
	}
	
	public CustomerDTO(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.cpf = customer.getCpf();
		this.email = customer.getEmail();
		this.profile = customer.getProfile();
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
