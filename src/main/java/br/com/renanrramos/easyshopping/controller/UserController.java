package br.com.renanrramos.easyshopping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.builder.UserBuilder;

@RestController
public class UserController {

	
	@RequestMapping("/users")
	public User index() {
		
		User user = new UserBuilder()
				.withName("Renan Ramos")
				.withProfile(Profile.ADMINISTRATOR)
				.build();
		
		return user;
	}
	
}
