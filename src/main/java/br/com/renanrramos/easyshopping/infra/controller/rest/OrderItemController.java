/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 26/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderItemDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderItemForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.infra.delegate.OrderItemDelegate;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductEntity;
import br.com.renanrramos.easyshopping.service.impl.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author renan.ramos
 */
@RestController
@RequestMapping(path = "api/order-items", produces = "application/json")
@Api(tags = "Order item")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemDelegate orderItemDelegate;

    private final ProductService productService;

    @ResponseBody
    @PostMapping
    @Transactional
    @ApiOperation(value = "Save a new order item")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<OrderItemDTO> saveOrderItem(@Valid @RequestBody OrderItemForm orderItemForm,
                                                      UriComponentsBuilder uriBuilder) throws EasyShoppingException {
        Long productId = orderItemForm.getProductId();

        // TODO: apply productId validation on ProductGateway implementation
        Optional<ProductEntity> productOptional = productService.findById(productId);

        if (productOptional.isEmpty()) {
            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_NOT_FOUND);
        }
        final OrderItemDTO newOrderItem = orderItemDelegate.save(orderItemForm);
        return ResponseEntity
                .created(uriBuilder
                        .path("/order-items/{id}")
                        .buildAndExpand(newOrderItem.getId()).encode().toUri())
                .body(newOrderItem);
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get order items")
    @RolesAllowed({"easy-shopping-user"})
    public ResponseEntity<PageResponse<OrderItemDTO>> getOrderItemsByOrderId(@PathVariable("id") Long orderId) {
        return ResponseEntity.ok(orderItemDelegate.findOrderItemByOrderId(orderId));
    }
}
