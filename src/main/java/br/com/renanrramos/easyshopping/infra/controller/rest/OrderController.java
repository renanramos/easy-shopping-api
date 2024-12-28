/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 24/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderForm;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.OrderEntity;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.OrderMapper;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
        OrderEntity order = OrderMapper.INSTANCE.mapOrderFormToOrder(orderForm);
        order.setCustomerId(authenticationServiceImpl.getName());
        order = orderService.save(order);
        uri = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(OrderMapper.INSTANCE.mapOrderToOrderDTO(order));
    }

    @ResponseBody
    @PatchMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Update order")
    @RolesAllowed({"CUSTOMER", "easy-shopping-user", "app-customer"})
    public ResponseEntity<OrderDTO> updateOrder(@Valid @RequestBody OrderForm orderForm,
                                                @PathVariable("id") Long orderId, UriComponentsBuilder uriBuilder) {

        Optional<OrderEntity> orderOptional = orderService.findById(orderId);

        if (!orderOptional.isPresent()) {
            throw new EntityNotFoundException(ExceptionMessagesConstants.ORDER_NOT_FOUND);
        }

        OrderEntity order = orderOptional.get();
        order.setFinished(true);
        order.setId(orderId);
        order = orderService.update(order);
        return ResponseEntity.accepted().body(OrderMapper.INSTANCE.mapOrderToOrderDTO(order));
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get orders")
    @RolesAllowed({"easy-shopping-user"})
    public ResponseEntity<List<OrderDTO>> getOrders() {

        List<OrderEntity> orders = orderService.findCustomerOrders(authenticationServiceImpl.getName());

        return ResponseEntity.ok(OrderMapper.INSTANCE.mapOrderListToOrderDTOList(orders));
    }
}
