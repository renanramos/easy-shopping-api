/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import javax.validation.constraints.Email;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.builder.CustomerBuilder;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author renan.ramos
 *
 */
public class CustomerForm {

	private String name;
	
	@Email
	private String email;
	
	private String cpf;
	
	@ApiModelProperty(hidden = true)
	private Profile profile;

	
	public CustomerForm() {
		
	}
	
	public CustomerForm(String name, String email, String cpf) {
		this.name = name;
		this.email = email;
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getCpf() {
		return cpf;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Customer converterCustomerFormToCustomer(CustomerForm customerForm) {
		return CustomerBuilder.builder()
				.withCpf(customerForm.getCpf())
				.withEmail(customerForm.getEmail())
				.withName(customerForm.getName())
				.build();
	}

	@Override
	public String toString() {
		return "CustomerForm [name=" + name + ", email=" + email + ", cpf=" + cpf + ", profile=" + profile + "]";
	}
}
