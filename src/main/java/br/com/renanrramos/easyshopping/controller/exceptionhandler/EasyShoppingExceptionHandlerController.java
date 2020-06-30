/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 29/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import br.com.renanrramos.easyshopping.constants.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.model.handleerror.ExceptionHandleEntity;

/**
 * @author renan.ramos
 *
 */
@RestControllerAdvice
public class EasyShoppingExceptionHandlerController {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionHandleEntity handleDataNotFound(EmptyResultDataAccessException exception) {
		return new ExceptionHandleEntity(exception.getMessage());
	}
	
	@ExceptionHandler(MethodNotAllowedException.class)
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	public ExceptionHandleEntity handleMethodNotAllowed(MethodNotAllowedException exception) {
		StringBuilder builderMessage = new StringBuilder();
		builderMessage.append(exception.getStatus());
		builderMessage.append(" : " + exception.getHttpMethod());
		builderMessage.append(ExceptionMessagesConstants.METHOD_NOT_ALLOWED_MESSAGE);
		return new ExceptionHandleEntity(builderMessage.toString());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<ExceptionHandleEntity> handleBadRequest(MethodArgumentNotValidException exception) {
		List<ExceptionHandleEntity> errors = new ArrayList<>();
		List<FieldError> fields = exception.getBindingResult().getFieldErrors();
		fields.forEach(field -> {
			String errorMessage = messageSource.getMessage(field, LocaleContextHolder.getLocale());
			ExceptionHandleEntity error = new ExceptionHandleEntity(field.getField(), errorMessage);
			errors.add(error);
		});
		return errors;
	}
}
