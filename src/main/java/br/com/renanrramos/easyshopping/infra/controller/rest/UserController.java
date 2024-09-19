/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 22/10/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import br.com.renanrramos.easyshopping.interfaceadapter.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.model.UserEntity;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.UserDTO;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
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

	@Autowired
	private AuthenticationServiceImpl authenticationServiceImpl;

	@ResponseBody
	@GetMapping(path = "/profile")
	@ApiOperation(value = "Get user profile info")
	@RolesAllowed({"ADMINISTRATOR", "CUSTOMER", "easy-shopping-user"})
	public ResponseEntity<UserDTO> getCustomerProfile() {

		Optional<UserEntity> user = userService.findUserByTokenId(authenticationServiceImpl.getName());
        return user.map(value -> ResponseEntity.
						ok(UserMapper.INSTANCE.mapUserToUserDTO(value)))
							.orElseGet(() -> ResponseEntity.ok().build());

    }

}
