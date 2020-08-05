/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.repository.UserRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class UserService implements CommonService<User>{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public Optional<User> findById(Long entityId) {
		return null;
	}

	@Override
	public User update(User entity) {
		return null;
	}

	@Override
	public void remove(Long entityId) {
	}
}
