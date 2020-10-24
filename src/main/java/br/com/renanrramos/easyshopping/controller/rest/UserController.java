/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 22/10/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.dto.UserDTO;
import br.com.renanrramos.easyshopping.service.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/users", produces = "application/json")
@Api(tags = "Users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@GetMapping(path = "/profile")
	@ApiOperation(value = "Get user profile info")
	@RolesAllowed({"ADMINISTRATOR", "CUSTOMER", "easy-shopping-user"})
	public ResponseEntity<UserDTO> getCustomerProfile(Principal principal) {

		Optional<User> user = userService.findUserByTokenId(principal.getName());
		if (user.isPresent()) {
			return ResponseEntity.ok(UserDTO.converterUserToUserDTO(user.get()));
		}

		return ResponseEntity.notFound().build();
	}

}
