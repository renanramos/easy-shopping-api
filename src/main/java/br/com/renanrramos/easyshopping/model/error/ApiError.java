/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 16/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author renan.ramos
 *
 */
public class ApiError {
	
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timeStamp;
	
	private String errorTitle;
	
	private String message;
	
	public ApiError() {
		this.timeStamp = LocalDateTime.now();
	}
	
	public ApiError(HttpStatus status) {
		this.timeStamp = LocalDateTime.now();
		this.status = status;
	}
	
	public ApiError(HttpStatus status, Throwable ex) {
		this.timeStamp = LocalDateTime.now();
		this.status = status;
		this.errorTitle = "Unexpected error";
		this.message = ex.getLocalizedMessage();
	}
	
	public ApiError(HttpStatus status, String errorTitle, Throwable ex) {
		this.timeStamp = LocalDateTime.now();
		this.status = status;
		this.errorTitle = errorTitle;
		this.message = ex.getLocalizedMessage();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getErrorTitle() {
		return errorTitle;
	}

	public String getMessage() {
		return message;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
