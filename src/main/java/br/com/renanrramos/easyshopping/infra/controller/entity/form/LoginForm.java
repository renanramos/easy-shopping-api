/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
public class LoginForm {

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	public LoginForm() {
		// Intentionally empty
	}

	public LoginForm(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
