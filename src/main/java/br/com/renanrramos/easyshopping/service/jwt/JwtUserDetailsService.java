/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.service.impl.UserService;

/**
 * @author renan.ramos
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService{

	private static final Logger LOG = LoggerFactory.getLogger(JwtUserDetailsService.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) {

		LOG.info("Email selecionado: {} ", email);
		
		Optional<br.com.renanrramos.easyshopping.model.User> userOptional = userService.findUserByEmail(email);
		if(!userOptional.isPresent()) {
			LOG.error("Email não encontrado: {} ", email);
			throw new UsernameNotFoundException(ExceptionMessagesConstants.INVALID_CREDENTIALS);
		}

		br.com.renanrramos.easyshopping.model.User userEasyShopping = userOptional.get();

		return new User(email, userEasyShopping.getName(), new ArrayList<>());
	}

}
