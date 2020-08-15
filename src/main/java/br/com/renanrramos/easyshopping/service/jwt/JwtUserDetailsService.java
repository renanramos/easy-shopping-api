/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.jwt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

	private Logger LOG = LoggerFactory.getLogger(JwtUserDetailsService.class);
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) {
		br.com.renanrramos.easyshopping.model.User user = userService.findUserByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(ExceptionMessagesConstants.USER_NOT_FOUND));
		LOG.info("User role : {}", user.getProfile().getRole());
		List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(user.getProfile().getRole());
		return new User(email, user.getName(), authorityList);
	}

}
