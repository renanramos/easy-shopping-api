/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.exception;

/**
 * @author renan.ramos
 *
 */
public class EasyShoppingException extends Exception{

	private static final long serialVersionUID = -4953620480615026679L;

	public EasyShoppingException() {
		// Intentionally empty
	}

	public EasyShoppingException(String errorMessage) {
		super(errorMessage);
	}
}
