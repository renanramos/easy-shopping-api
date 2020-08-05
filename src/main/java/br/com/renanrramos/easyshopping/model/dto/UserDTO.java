/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 05/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.User;
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
public class UserDTO {
	
	private String name;

	private Profile profile;

	public UserDTO() {
		// Intentionally empty
	}

	public UserDTO(User user) {
		this.name = user.getName();
		this.profile = user.getProfile();
	}

	public static UserDTO converterUserToUserDTO(User user) {
		return new UserDTO(user);
	}
}
