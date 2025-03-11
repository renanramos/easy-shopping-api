/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@RequiredArgsConstructor
public class LoginForm {

	@NotBlank
	private String email;

	@NotBlank
	private String password;

}
