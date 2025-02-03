/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 24/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.delegate.OrderDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

/**
 * @author renan.ramos
 */
@RestController
@RequestMapping(path = "api/orders", produces = "application/json")
@Api(tags = "Order")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderDelegate orderDelegate;

    private URI uri;

    @ResponseBody
    @PostMapping
    @Transactional
    @ApiOperation(value = "Save a new order")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<OrderDTO> saveOrder(@Valid @RequestBody final OrderForm orderForm,
                                              final UriComponentsBuilder uriBuilder) {
        final OrderDTO orderDTO = orderDelegate.save(orderForm);
        uri = uriBuilder.path("/orders/{id}").buildAndExpand(orderDTO.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(orderDTO);
    }

    @ResponseBody
    @PatchMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Update order")
    @RolesAllowed({"CUSTOMER", "easy-shopping-user", "app-customer"})
    public ResponseEntity<OrderDTO> updateOrder(@Valid @RequestBody OrderForm orderForm,
                                                @PathVariable("id") Long orderId, UriComponentsBuilder uriBuilder) {
        final OrderDTO orderDTO = orderDelegate.update(orderForm, orderId);
        uri = uriBuilder.path("/orders/{id}").buildAndExpand(orderDTO.getId()).encode().toUri();
        return ResponseEntity
                .status(HttpStatus.OK)
                .location(uri).body(orderDTO);
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get orders")
    @RolesAllowed({"easy-shopping-user"})
    public ResponseEntity<PageResponse<OrderDTO>> getOrders() {
        return ResponseEntity.ok(orderDelegate.findCustomerOrders());
    }
}
