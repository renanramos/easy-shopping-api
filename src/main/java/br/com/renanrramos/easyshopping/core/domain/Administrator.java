/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;

/**
 * @author renan.ramos
 *
 */
public class Administrator extends User {

	private Profile profile = Profile.ADMINISTRATOR;

	public Administrator() {
		// Intentionally empty
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "AdministratorEntity [profile=" + profile + "]";
	}
}
