/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 29/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
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
				.withErrors(new ArrayList<>())
				.build());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiErrorBuilder()
				.withStatus(status)
				.withErrorTitle(status.getReasonPhrase())
				.withMessage(ex.getMessage())
				.withErrors(new ArrayList<>())
				.build());
	}

	@ExceptionHandler({TransactionSystemException.class})
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final Exception ex, final WebRequest request) {
		Throwable cause = ((TransactionSystemException) ex).getRootCause();
        final List<String> errors = new ArrayList<>();
		if (cause instanceof ConstraintViolationException) {
			ConstraintViolationException consEx= (ConstraintViolationException) cause;
	        for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
	            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
	        }
			
	        return buildResponseEntity(new ApiErrorBuilder()
				.withStatus(HttpStatus.BAD_REQUEST)
				.withErrorTitle(ExceptionMessagesConstants.INVALID_FIELDS_TITLE)
				.withMessage(ExceptionMessagesConstants.INVALID_FIELDS_TITLE)
				.withErrors(errors)
				.build());
		}
		return buildResponseEntity(new ApiErrorBuilder()
				.withStatus(HttpStatus.BAD_REQUEST)
				.withErrorTitle(ExceptionMessagesConstants.ERRORS_FOUND)
				.withMessage(ex.getLocalizedMessage())
				.withErrors(new ArrayList<>())
				.build());
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		return buildResponseEntity(new ApiErrorBuilder()
				.withStatus(HttpStatus.NOT_FOUND)
				.withMessage(ex.getMessage())
				.withErrorTitle(ExceptionMessagesConstants.NOT_FOUND_TITLE)
				.withErrors(new ArrayList<>())
				.build());
	}

	@ExceptionHandler(EasyShoppingException.class)
	protected ResponseEntity<Object> handleEmailUnavailable(EasyShoppingException ex) {
		return buildResponseEntity(new ApiErrorBuilder()
				.withStatus(HttpStatus.CONFLICT)
				.withErrorTitle(ExceptionMessagesConstants.INVALID_FIELDS_TITLE)
				.withMessage(ex.getMessage())
				.withErrors(new ArrayList<>())
				.build());
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
