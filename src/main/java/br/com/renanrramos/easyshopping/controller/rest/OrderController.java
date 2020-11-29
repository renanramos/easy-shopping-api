/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 24/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.dto.OrderDTO;
import br.com.renanrramos.easyshopping.model.form.OrderForm;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new order")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<OrderDTO> saveOrder(@Valid @RequestBody OrderForm orderForm,
			UriComponentsBuilder uriBuilder) {
		Order order = OrderForm.converterOrderFormToOrder(orderForm);
		order.setCustomerId(authenticationServiceImpl.getName());
		order = orderService.save(order);
		uri = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).encode().toUri();
		return ResponseEntity.created(uri).body(OrderDTO.converterOrderToOrderDTO(order));
	}

	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update order")
	@RolesAllowed({ "CUSTOMER", "easy-shopping-user", "app-customer" })
	public ResponseEntity<OrderDTO> updateOrder(@Valid @RequestBody OrderForm orderForm,
			@PathVariable("id") Long orderId, UriComponentsBuilder uriBuilder) {

		Optional<Order> orderOptional = orderService.findById(orderId);

		if (!orderOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ORDER_NOT_FOUND);
		}

		Order order = orderOptional.get();
		order.setFinished(true);
		order.setId(orderId);
		order = orderService.update(order);
		return ResponseEntity.accepted().body(OrderDTO.converterOrderToOrderDTO(order));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get orders")
	@RolesAllowed({ "easy-shopping-user" })
	public ResponseEntity<List<OrderDTO>> getOrders() {

		List<Order> orders = orderService.findCustomerOrders(authenticationServiceImpl.getName());

		return ResponseEntity.ok(OrderDTO.converterOrderListToOrderDTOList(
				orders.stream().filter(order -> !order.isFinished()).collect(Collectors.toList())));
	}
}
