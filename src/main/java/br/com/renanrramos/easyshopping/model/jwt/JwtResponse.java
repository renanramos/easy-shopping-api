/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.jwt;

import java.io.Serializable;

import lombok.Getter;

/**
 * @author renan.ramos
 *
 */
@Getter
public class JwtResponse implements Serializable{

	private static final long serialVersionUID = 1631739111032561633L;

	private String token;

	public JwtResponse() {
		// Intentionally empty
	}

	public JwtResponse(String token) {
		this.token = token;
	}
}
