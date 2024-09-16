/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 30/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author renan.ramos
 *
 */
public class OAuth2AuthenticationException extends AuthenticationException{

	private static final long serialVersionUID = -5629221218770830818L;

	public OAuth2AuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	public OAuth2AuthenticationException(String msg) {
		super(msg);
	}
}
