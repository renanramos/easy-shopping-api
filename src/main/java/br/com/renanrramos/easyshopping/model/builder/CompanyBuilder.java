/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Company;

/**
 * @author renan.ramos
 *
 */
public class CompanyBuilder {

	private Company company;
	
	public CompanyBuilder() {
		this.company = new Company();
	}
	
	public static CompanyBuilder builder() {
		return new CompanyBuilder();
	}
	
	public CompanyBuilder withRegisteredNumber(String registeredNumber) {
		this.company.setRegisteredNumber(registeredNumber);
		return this;
	}
	
	public CompanyBuilder withPhone(String phone) {
		this.company.setPhone(phone);
		return this;	
	}
	
	public CompanyBuilder withEmail(String email) {
		this.company.setEmail(email);
		return this;
	}
	
	public Company build() {
		this.company.setProfile(Profile.COMPANY);
		return this.company;
	}
}
