/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.config.util.JwtTokenUtil;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.form.LoginForm;
import br.com.renanrramos.easyshopping.model.jwt.AuthenticationResponse;
import br.com.renanrramos.easyshopping.service.impl.UserService;
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
	private UserService userService;
	
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

	@PostMapping(path = "/logout")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> logout(HttpServletRequest request) {
		HttpHeaders responseHeaders = new HttpHeaders();
		AuthenticationResponse response = new AuthenticationResponse();
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null) {
			
			String token = authHeader.replace("Bearer ", "").trim();
			response.setToken(token);
		}

		return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(path = "/profile")
	public ResponseEntity<?> userInfo(@RequestParam Long userId) throws EasyShoppingException {

		Long currentUserId = userService.getCurrentUserId();

		if (!currentUserId.equals(userId)) {
			throw new EasyShoppingException(ExceptionMessagesConstants.WRONG_USER_ID);
		}

		Optional<User> userOptional = userService.findUserById(userId);

		if (!userOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.USER_NOT_FOUND);
		}

		return ResponseEntity.ok(userOptional.get());
	}
}
