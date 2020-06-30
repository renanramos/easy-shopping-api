/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 29/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.handleerror;

/**
 * @author renan.ramos
 *
 */
public class ExceptionHandleEntity {

	private String field;
	private String error;
	
	public ExceptionHandleEntity(String error) {
		this.error = error;
	}
	
	public ExceptionHandleEntity(String field, String error) {
		this.field = field;
		this.error = error;
	}
	
	public String getField() {
		return field;
	}
	public String getError() {
		return error;
	}	
}
