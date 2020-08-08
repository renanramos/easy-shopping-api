/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 30/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.builder.CompanyBuilder;
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
public class CompanyForm {

	private String registeredNumber;

	private String name;

	private String email;

	private String password;
	
	private String phone;

	public CompanyForm() {
		// Intentionally empty
	}

	public CompanyForm(String name, String registeredNumber, String email, String phone, String password) {
		this.name = name;
		this.registeredNumber = registeredNumber;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	public static Company converterCompanyFormToCompany(CompanyForm companyForm) {
		return CompanyBuilder.builder()
				.withPassword(companyForm.getPassword())
				.withName(companyForm.getName())
				.withEmail(companyForm.getEmail())
				.withPhone(companyForm.getPhone())
				.withRegisteredNumber(companyForm.getRegisteredNumber())
				.build();
	}

	@Override
	public String toString() {
		return "CompanyForm [registeredNumber=" + registeredNumber + ", name=" + name + ", email=" + email + ", phone="
				+ phone + "]";
	}

}
