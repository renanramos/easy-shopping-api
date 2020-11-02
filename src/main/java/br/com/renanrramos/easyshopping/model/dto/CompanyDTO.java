/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 30/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Company;
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
public class CompanyDTO {

	private Long id;
	
	private String name;
	
	private String registeredNumber;
	
	private String email;

	private String phone;

	private Profile profile;

	private boolean isActive;

	public CompanyDTO() {
		// Intentionally empty
	}
	
	public CompanyDTO(Company company) {
		this.id = company.getId();
		this.name = company.getName();
		this.registeredNumber = company.getRegisteredNumber();
		this.email = company.getEmail();
		this.phone = company.getPhone();
		this.profile = company.getProfile();
	}

	public static List<CompanyDTO> converterCompanyListToCompanyDTOList(List<Company> companies) {
		return companies.stream().map(CompanyDTO::new).collect(Collectors.toList());
	}
	
	public static CompanyDTO converterToCompanyDTO(Company company) {
		return new CompanyDTO(company);
	}

	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", name=" + name + ", registeredNumber=" + registeredNumber + ", email=" + email
				+ ", phone=" + phone + ", profile=" + profile + ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getRegisteredNumber()=" + getRegisteredNumber() + ", getEmail()=" + getEmail() + ", getPhone()="
				+ getPhone() + ", getProfile()=" + getProfile() + "]";
	}
}
