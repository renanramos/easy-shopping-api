/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 22/10/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.UserDTO;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.UserEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.UserMapper;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationService;
import br.com.renanrramos.easyshopping.service.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

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
    private AuthenticationService authenticationServiceImpl;

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
