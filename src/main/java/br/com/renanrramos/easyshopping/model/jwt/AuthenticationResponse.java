/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.jwt;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
public class AuthenticationResponse implements Serializable{

	private static final long serialVersionUID = 1631739111032561633L;

	private Long id;

	private String email;

	private String username;

	private List<String> roles;
	
	private String token;

	public AuthenticationResponse() {
		// Intentionally empty
	}

	public AuthenticationResponse(String token) {
		this.token = token;
	}
}
