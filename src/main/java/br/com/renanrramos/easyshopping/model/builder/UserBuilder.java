/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.User;

/**
 * @author renan.ramos
 *
 */
public class UserBuilder {

	private User user;

	public UserBuilder() {
		this.user = new User();
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	public UserBuilder withEmail(String email) {
		this.user.setEmail(email);
		return this;
	}

	public UserBuilder withPassword(String password) {
		this.user.setPassword(password);
		return this;
	}

	public UserBuilder withName(String name) {
		this.user.setName(name);
		return this;
	}

	public UserBuilder withProfile(Profile profile) {
		this.user.setProfile(profile);
		return this;
	}

	public User build() {
		return this.user;
	}
}
