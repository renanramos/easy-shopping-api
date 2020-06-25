package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.User;

public class UserBuilder {

	private String name;
	private Profile profile;
	
	public UserBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public UserBuilder withProfile(Profile profile) {
		this.profile = profile;
		return this;
	}
	
	public User build() {
		return new User(this.name, this.profile);
	}
}
