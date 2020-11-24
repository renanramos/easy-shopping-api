/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 24/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.OrderService;
import io.swagger.annotations.Api;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/orders", produces = "application/json")
@Api(tags = "Order")
@CrossOrigin(origins = "*")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private AuthenticationServiceImpl authenticationServiceImpl;

	private URI uri;

}
