/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.util.Optional;

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

import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.dto.UserDTO;
import br.com.renanrramos.easyshopping.model.form.LoginForm;
import br.com.renanrramos.easyshopping.service.impl.UserService;
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
	private UserService userService;

	private URI uri;

	@ResponseBody
	@PostMapping(path = "/login")
	@Transactional
	public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginForm loginForm) {
		Optional<User> userOptional = userService.login(loginForm);
		if (userOptional.isPresent()) {
			return ResponseEntity.ok(UserDTO.converterUserToUserDTO(userOptional.get()));			
		}
		return ResponseEntity.notFound().build();
	}
}
