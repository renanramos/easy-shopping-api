/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.builder.CustomerBuilder;
import io.swagger.annotations.ApiModelProperty;
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
public class CustomerForm {

	private String name;

	private String email;
	
	private String cpf;
	
	@ApiModelProperty(hidden = true)
	private Profile profile;

	private String password;

	public CustomerForm() {
		// Intentionally empty
	}
	
	public CustomerForm(String name, String email, String cpf, String password) {
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.password = password;
	}

	public static Customer converterCustomerFormToCustomer(CustomerForm customerForm) {
		return CustomerBuilder.builder()
				.withPassword(customerForm.getPassword())
				.withCpf(customerForm.getCpf())
				.withEmail(customerForm.getEmail())
				.withName(customerForm.getName())
				.build();
	}

	public static Customer converterCustomerFormUpdateToCustomer(CustomerForm customerForm, Customer currentCustomer) {
		
		return CustomerBuilder.builder()
				.withPassword(Optional.ofNullable(customerForm.getPassword()).orElse(currentCustomer.getPassword()))
				.withCpf(Optional.ofNullable(customerForm.getCpf()).orElse(currentCustomer.getCpf()))
				.withEmail(Optional.ofNullable(customerForm.getEmail()).orElse(currentCustomer.getEmail()))
				.withName(Optional.ofNullable(customerForm.getName()).orElse(currentCustomer.getName()))
				.build();
	}
	
	@Override
	public String toString() {
		return "CustomerForm [name=" + name + ", email=" + email + ", cpf=" + cpf + ", profile=" + profile + "]";
	}
}
