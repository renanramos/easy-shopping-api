/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 16/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.error.builder;

import org.springframework.http.HttpStatus;

import br.com.renanrramos.easyshopping.model.error.ApiError;

/**
 * @author renan.ramos
 *
 */
public class ApiErrorBuilder {

	private ApiError apiError;

	public ApiErrorBuilder() {
		this.apiError = new ApiError();
	}
	
	public ApiErrorBuilder builder() {
		return new ApiErrorBuilder();
	}
	
	public ApiErrorBuilder withStatus(HttpStatus status) {
		this.apiError.setStatus(status);
		return this;
	}
	
	public ApiErrorBuilder withErrorTitle(String errorTitle) {
		this.apiError.setErrorTitle(errorTitle);
		return this;
	}
	
	public ApiErrorBuilder withMessage(String message) {
		this.apiError.setMessage(message);
		return this;
	}
	
	public ApiError build() {
		return this.apiError;
	}
}
