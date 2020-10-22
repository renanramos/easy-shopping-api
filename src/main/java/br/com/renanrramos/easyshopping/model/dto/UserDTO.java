/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 22/10/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.Optional;

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

	private String email;

	public UserDTO() {
		// Intentionally empty
	}
	
	public UserDTO(User user) {
		this.name = Optional.ofNullable(user.getName()).orElse("");
		this.email = Optional.ofNullable(user.getEmail()).orElse("");
	}

	public static UserDTO converterUserToUserDTO(User user) {
		return new UserDTO(user);
	}
}
