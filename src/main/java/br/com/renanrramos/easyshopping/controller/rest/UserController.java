/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.config.JwtTokenUtil;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.model.form.LoginForm;
import br.com.renanrramos.easyshopping.model.jwt.JwtResponse;
import br.com.renanrramos.easyshopping.service.jwt.JwtUserDetailsService;
import io.swagger.annotations.Api;

/**
 * @author renan.ramos
 *
 */
@RestController
@CrossOrigin
@RequestMapping(path = "api/users", produces = "application/json")
@Api(tags = "Users")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@ResponseBody
	@PostMapping(path = "/login")
	@Transactional
	public ResponseEntity<Object> login(@Valid @RequestBody LoginForm loginForm) throws EasyShoppingException {
		authentication(loginForm.getEmail(), loginForm.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authentication(String email, String password) throws EasyShoppingException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));			
		} catch (DisabledException ex) {
			throw new EasyShoppingException(ExceptionMessagesConstants.USER_DISABLED);
		} catch (BadCredentialsException ex) {
			throw new EasyShoppingException(ExceptionMessagesConstants.INVALID_CREDENTIALS);
		}
	}
}
