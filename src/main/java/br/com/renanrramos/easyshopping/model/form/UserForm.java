/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.builder.UserBuilder;
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
public class UserForm {

	private String name;

	private String email;

	private Profile profile;

	public UserForm() {
		// Intentionally empty
	}

	public UserForm(String name, String email, Profile profile) {
		this.name = name;
		this.email = email;
		this.profile = profile;
	}

	public static User converterUserFormToUser(UserForm userForm) {
		return UserBuilder.builder()
				.withName(userForm.getName())
				.withEmail(userForm.getEmail())
				.withProfile(userForm.getProfile())
				.build();
	}

	@Override
	public String toString() {
		return "UserForm [name=" + name + ", email=" + email + ", profile=" + profile + "]";
	}
}
