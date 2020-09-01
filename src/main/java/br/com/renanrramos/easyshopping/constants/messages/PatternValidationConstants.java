/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 07/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.constants.messages;

/**
 * @author renan.ramos
 *
 */
public class PatternValidationConstants {

	public PatternValidationConstants() {
		// Intentionally empty
	}

	public static final String CPF_PATTERN = "^[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$";
	public static final String REGISTERED_NUMBER_PATTERN = "^[0-9]{2}[.][0-9]{3}[.][0-9]{3}['\'/][0-9]{4}[-][0-9]{2}$";
}
