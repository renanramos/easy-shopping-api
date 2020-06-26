/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

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
	
	public CompanyBuilder withCnpj(String cnpj) {
		this.company.setCnpj(cnpj);
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
		return this.company;
	}
}
