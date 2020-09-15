/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.enums;

import lombok.Getter;

@Getter
public enum Profile {

	CUSTOMER("CUSTOMER"),
	COMPANY("COMPANY"),
	ADMINISTRATOR("ADMINISTRATOR");

	private String role;
	
	Profile(String profile) {
		this.role = "ROLE_" + profile.toUpperCase();
	}

	public static String getProfileName(Profile profile) {
		return profile.name().toUpperCase();
	}

	public static String transformRoleToProfile(String role) {
		return role.isEmpty() ? "" : role.substring(5);
	}
}
