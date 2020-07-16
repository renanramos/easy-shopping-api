/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 29/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.exceptionhandler;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.renanrramos.easyshopping.model.error.ApiError;
import br.com.renanrramos.easyshopping.model.error.builder.ApiErrorBuilder;

/**
 * @author renan.ramos
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "br.com.renanrramos.easyshopping")
public class ExceptionHandlerController extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new ApiErrorBuilder()
				.withStatus(HttpStatus.BAD_REQUEST)
				.withErrorTitle(error)
				.withMessage(ex.getMessage())
				.build());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFoun(EntityNotFoundException ex) {
		return buildResponseEntity(new ApiErrorBuilder()
				.withStatus(HttpStatus.BAD_REQUEST)
				.withMessage(ex.getMessage())
				.build());
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
