/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 26/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.OrderItemDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.OrderItemForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.infra.delegate.OrderItemDelegate;
import br.com.renanrramos.easyshopping.infra.delegate.ProductDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

;

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

    private final ProductDelegate productDelegate;

    @ResponseBody
    @PostMapping
    @Transactional
    @ApiOperation(value = "Save a new order item")
    @RolesAllowed("easy-shopping-user")
    public ResponseEntity<OrderItemDTO> saveOrderItem(@Valid @RequestBody OrderItemForm orderItemForm,
                                                      UriComponentsBuilder uriBuilder) throws EasyShoppingException {
        Long productId = orderItemForm.getProductId();

        // TODO: apply productId validation on ProductGateway implementation
        final ProductDTO productOptional = productDelegate.findById(productId);

        // TODO: review this validation
//        if (productOptional.isEmpty()) {
//            throw new EasyShoppingException(ExceptionConstantMessages.PRODUCT_NOT_FOUND);
//        }
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
