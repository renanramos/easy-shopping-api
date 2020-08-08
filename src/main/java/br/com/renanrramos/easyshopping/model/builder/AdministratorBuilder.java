/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Administrator;

/**
 * @author renan.ramos
 *
 */
public class AdministratorBuilder {

	private Administrator administrator;
	
	private AdministratorBuilder() {
		this.administrator = new Administrator();
	}
	
	public static AdministratorBuilder builder() {
		return new AdministratorBuilder();
	}

	public AdministratorBuilder withPassword(String password) {
		this.administrator.setPassword(password);
		return this;
	}

	public AdministratorBuilder withEmail(String email) {
		this.administrator.setEmail(email);
		return this;
	}

	public AdministratorBuilder withName(String name) {
		this.administrator.setName(name);
		return this;
	}
	
	public Administrator build() {
		this.administrator.setProfile(Profile.ADMINISTRATOR);
		return this.administrator;
	}
}
