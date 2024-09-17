/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;

public class User {

	private Long id;
	private String name;
	private String email;
	private Profile profile;
	private String tokenId;

	private boolean isSync;

	public User() {
		// Intentionally empty
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public boolean isSync() {
		return isSync;
	}

	public void setSync(boolean sync) {
		isSync = sync;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}
