/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 13/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.enums;

import lombok.Getter;

/**
 * @author renan.ramos
 *
 */
@Getter
public enum AuthProvider {
	LOCAL("LOCAL"),
	FACEBOOK("FACEBOOK"),
	GOOGLE("GOOGLE"),
	GITHUB("GITHUB");

	private String provider;

	AuthProvider() {
		// Intentionally empty
	}

	AuthProvider(String provider) {
		this.provider = provider;
	}

	public static String getProvider(AuthProvider provider) {
		return provider.name().toUpperCase();
	}
}
