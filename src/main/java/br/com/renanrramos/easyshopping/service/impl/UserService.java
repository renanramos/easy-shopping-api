/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.enums.Profile;
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

	private Authentication auth;

	public boolean isUserEmailAlreadyInUse(String email) {
		Optional<User> user = userRepository.findTopUserByEmail(email);
		return user.isPresent();
	}

	public Optional<User> login(LoginForm loginForm) {
		return userRepository.findTopUserByEmail(loginForm.getEmail());
	}

	public Optional<User> findUserByEmailAndPassword(String email) {
		return userRepository.findTopUserByEmail(email);
	}

	@SuppressWarnings("unchecked")
	public Long getCurrentUserId() {
		auth = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> credentials = (Map<String, Object>) auth.getCredentials();
		return Long.parseLong(credentials.get("userId").toString());
	}

	public String getCurrentUserRole() {
		auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().isEmpty()) {
			return "";
		}
		List<GrantedAuthority> authorities = auth.getAuthorities().stream().collect(Collectors.toList());
		return Profile.transformRoleToProfile(authorities.get(0).getAuthority());
	}

}
