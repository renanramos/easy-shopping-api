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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.config.util.JwtTokenUtil;
import br.com.renanrramos.easyshopping.model.User;
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
@RequestMapping(path = "/users", produces = "application/json")
@Api(tags = "Users")
public class UserController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@ResponseBody
	@PostMapping(path = "/authentication")
	@Transactional
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginForm loginForm) {
		User user = userDetailsService.loadUserByUsernameAndPassword(loginForm);
		String token = jwtTokenUtil.generateToken(user);
		return ResponseEntity.ok(new JwtResponse(token));
	}
}
