/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 30/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

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

	private String email;

	private String name;

	private String registeredNumber;

	private String phone;

	public CompanyForm() {
		// Intentionally empty
	}

	public CompanyForm(String registeredNumber, String phone) {
		this.registeredNumber = registeredNumber;
		this.phone = phone;
	}

	public static Company converterCompanyFormToCompany(CompanyForm companyForm) {
		return CompanyBuilder.builder()
				.withEmail(companyForm.getEmail()).withName(companyForm.getName())
				.withPhone(companyForm.getPhone())
				.withRegisteredNumber(companyForm.getRegisteredNumber())
				.build();
	}

	public static Company converterCompanyFormUpdateToCompany(CompanyForm companyForm, Company currentCompany) {
		return CompanyBuilder.builder()
				.withName(Optional.ofNullable(companyForm.getName()).orElse(currentCompany.getName()))
				.withEmail(Optional.ofNullable(companyForm.getEmail()).orElse(currentCompany.getEmail()))
				.withPhone(Optional.ofNullable(companyForm.getPhone()).orElse(currentCompany.getPhone()))
				.withRegisteredNumber(Optional.ofNullable(companyForm.getRegisteredNumber()).orElse(currentCompany.getRegisteredNumber()))
				.build();
	}

	@Override
	public String toString() {
		return "CompanyForm [registeredNumber=" + registeredNumber + ", phone="
				+ phone + "]";
	}

}
