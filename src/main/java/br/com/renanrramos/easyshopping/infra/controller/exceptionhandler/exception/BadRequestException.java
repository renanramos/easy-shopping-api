/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 30/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author renan.ramos
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = -4163949929142520186L;

	public BadRequestException(String msg) {
		super(msg);
	}

	public BadRequestException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
