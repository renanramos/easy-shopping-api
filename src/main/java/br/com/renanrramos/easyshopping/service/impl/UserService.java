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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.UserRepository;
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
	public User save(User entity) {
		// Not used
		return null;
	}

	@Override
	public List<User> findAll(Pageable page) {
		return userRepository.findAll(page).getContent();
	}

	@Override
	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User update(User entity) {
		// Not used
		return null;
	}

	@Override
	public void remove(Long entityId) {
		// Not used
	}

	@Override
	public List<User> findAllPageable(Pageable page, Long id) {
		// Not used
		return null;
	}

	public Optional<User> findUserByTokenId(String tokenId) {
		return userRepository.findUserByTokenId(tokenId);
	}
}
