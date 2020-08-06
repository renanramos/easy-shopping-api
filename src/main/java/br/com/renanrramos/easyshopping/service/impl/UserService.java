/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.repository.UserRepository;

/**
 * @author renan.ramos
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean isValidUserEmail(String email) {
		List<User> user = userRepository.getUserByEmail(email);
		return user.isEmpty();
	}
}
