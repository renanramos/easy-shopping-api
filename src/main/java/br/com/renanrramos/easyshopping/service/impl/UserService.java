/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.form.LoginForm;
import br.com.renanrramos.easyshopping.repository.UserRepository;

/**
 * @author renan.ramos
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean isUserEmailInvalid(String email) {
		User user = userRepository.findTopUserByEmail(email);
		return Optional.ofNullable(user).isPresent();
	}

	public Optional<User> login(LoginForm loginForm) {
		return userRepository.findTopUserByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
	}
}
