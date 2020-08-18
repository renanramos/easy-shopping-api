/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.config.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author renan.ramos
 *
 */
@Component
public class EasyShoppingUtils {

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public EasyShoppingUtils() {
		// Intentionally empty
	}

	public String encodePassword(String password) {
		return bcryptEncoder.encode(password);
	}

	public boolean verifyPassword(String password, String encodedPassword) {
		return bcryptEncoder.matches(password, encodedPassword);
	}
}
