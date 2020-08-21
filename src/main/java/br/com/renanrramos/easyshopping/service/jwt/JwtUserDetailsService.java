/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.jwt;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.config.util.EasyShoppingUtils;
import br.com.renanrramos.easyshopping.config.util.JwtTokenUtil;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.model.form.LoginForm;
import br.com.renanrramos.easyshopping.service.impl.UserService;

/**
 * @author renan.ramos
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	private Logger LOG = LoggerFactory.getLogger(JwtUserDetailsService.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private EasyShoppingUtils utils;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDetails loadUserByUsername(String email) {
		br.com.renanrramos.easyshopping.model.User user = userService.findUserByEmailAndPassword(email)
				.orElseThrow(() -> new UsernameNotFoundException(ExceptionMessagesConstants.USER_NOT_FOUND));
		return new User(email, user.getName(), jwtTokenUtil.createUserAuthorityList(user));
	}

	public br.com.renanrramos.easyshopping.model.User loadUserByUsernameAndPassword(LoginForm loginForm) {
		Optional<br.com.renanrramos.easyshopping.model.User> userOptional = userService.findUserByEmailAndPassword(loginForm.getEmail());

		if (!userOptional.isPresent()) {
			throw new UsernameNotFoundException(ExceptionMessagesConstants.INVALID_CREDENTIALS);
		}

		br.com.renanrramos.easyshopping.model.User user = userOptional.get();

		if (!utils.verifyPassword(loginForm.getPassword(), user.getPassword())) {
			throw new UsernameNotFoundException(ExceptionMessagesConstants.INVALID_CREDENTIALS);
		}

		LOG.info("User role : {}", user.getProfile().getRole());
		return user;
	}
}
