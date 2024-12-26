/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 26/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.renanrramos.easyshopping.interfaceadapter.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Order;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderItem;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.Product;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderItemDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderItemForm;
import br.com.renanrramos.easyshopping.service.impl.OrderItemService;
import br.com.renanrramos.easyshopping.service.impl.OrderService;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/order-items", produces = "application/json")
@Api(tags = "Order item")
@CrossOrigin(origins = "*")
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	private URI uri;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new order item")
	@RolesAllowed("easy-shopping-user")
	public ResponseEntity<OrderItemDTO> saveOrderItem(@Valid @RequestBody OrderItemForm orderItemForm,
			UriComponentsBuilder uriBuilder) throws EasyShoppingException {

		Long orderId =  orderItemForm.getOrderId();

		if (orderId == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ORDER_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Order> orderOptional = orderService.findById(orderId);

		if (!orderOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.ORDER_NOT_FOUND);
		}

		Long productId = orderItemForm.getProductId();

		if (productId == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.PRODUCT_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Product> productOptional = productService.findById(productId);

		if (!productOptional.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.PRODUCT_NOT_FOUND);
		}

		OrderItem orderItem = OrderItemMapper.INSTANCE.mapOrderItemFormToOrderItem(orderItemForm);
		orderItem.setOrder(orderOptional.get());

		orderItem = orderItemService.save(orderItem);
		uri = uriBuilder.path("/order-items/{id}").buildAndExpand(orderItem.getId()).encode().toUri();
		return ResponseEntity.created(uri).body(OrderItemMapper.INSTANCE.mapOrderItemToOrderItemDTO(orderItem));
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get order items")
	@RolesAllowed({ "easy-shopping-user" })
	public ResponseEntity<List<OrderItemDTO>> getOrderItemsByOrderId(@PathVariable("id") Long orderId) {
		List<OrderItemDTO> orderItemsDTO = OrderItemMapper.INSTANCE
				.mapOrderItemListToOrderItemDTOList(orderItemService.findOrderItemByOrderId(orderId));
		return ResponseEntity.ok(orderItemsDTO);
	}
}
