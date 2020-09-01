/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.util.Arrays;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.config.util.JwtTokenUtil;
import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.form.LoginForm;
import br.com.renanrramos.easyshopping.model.jwt.AuthenticationResponse;
import br.com.renanrramos.easyshopping.service.jwt.JwtUserDetailsService;
import io.swagger.annotations.Api;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
@Api(tags = "Users")
public class UserController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@ResponseBody
	@PostMapping(path = "/login")
	@Transactional
	public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm) {
		HttpHeaders responseHeaders = new HttpHeaders();

		User user = userDetailsService.loadUserByUsernameAndPassword(loginForm);
		String token = jwtTokenUtil.generateToken(user);

		AuthenticationResponse response = new AuthenticationResponse();
		response.setId(user.getId());
		response.setUsername(user.getName());
		response.setEmail(user.getEmail());
		response.setToken(token);

		String role = Profile.getProfileName(user.getProfile());

		response.setRoles(Arrays.asList(role));

		return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
	}
}
