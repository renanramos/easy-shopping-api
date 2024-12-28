/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.Address;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.PurchaseDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.PurchaseForm;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.*;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.PurchaseMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.PurchaseStatisticMapper;
import br.com.renanrramos.easyshopping.service.impl.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/purchases", produces = "application/json")
@Api(tags = "Purchases")
@CrossOrigin(origins = "*")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private StockItemService stockItemService;

//	@Autowired
//	private AddressService addressService;

//	@Autowired
//	private CreditCardService creditCardService;

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    private URI uri;

    @ResponseBody
    @PostMapping
    @Transactional
    @ApiOperation(value = "Save a new purchase")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<PurchaseDTO> savePurchase(@Valid @RequestBody PurchaseForm purchaseForm,
                                                    UriComponentsBuilder uriBuilder) throws EasyShoppingException {

        if (purchaseForm.getOrderId() == null) {
            throw new EasyShoppingException(ExceptionMessagesConstants.ORDER_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<OrderEntity> orderOptional = orderService.findById(purchaseForm.getOrderId());

        if (!orderOptional.isPresent()) {
            throw new EasyShoppingException(ExceptionMessagesConstants.ORDER_NOT_FOUND);
        }

        if (purchaseForm.getAddressId() == null) {
            throw new EasyShoppingException(ExceptionMessagesConstants.ADDRESS_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<Address> addressOptional = Optional.empty();//addressService.findById(purchaseForm.getAddressId());

//		if (!addressOptional.isPresent()) {
//			throw new EasyShoppingException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
//		}

        if (purchaseForm.getCreditCardId() == null) {
            throw new EasyShoppingException(ExceptionMessagesConstants.CREDIT_CARD_ID_NOT_FOUND_ON_REQUEST);
        }

        Optional<CreditCardEntity> creditCardOptional = Optional.empty();//creditCardService.findById(purchaseForm.getCreditCardId());

//		if (!creditCardOptional.isPresent()) {
//			throw new EasyShoppingException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
//		}

        Purchase purchase = new Purchase();
        purchase.setOrder(orderOptional.get());
//		purchase.setAddress(addressOptional.get());
//		purchase.setCreditCard(creditCardOptional.get());
        purchase.setCustomerId(authenticationServiceImpl.getName());
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase = purchaseService.save(purchase);

        OrderEntity order = orderOptional.get();
        order.setFinished(true);
        order.setPurchase(purchase);
        orderService.update(order);

        order.getItems().stream().forEach(item -> {
            StockItem stockItem = stockItemService.findStockItemByProductId(item.getProductId())
                    .orElse(new StockItem());
            if (stockItem.getId() != null) {
                Integer amount = item.getAmount();
                Integer currentAmount = stockItem.getCurrentAmount();
                stockItem.setCurrentAmount(currentAmount - amount);
                stockItem = stockItemService.update(stockItem);
            }
        });

        uri = uriBuilder.path("/purchases/{id}").buildAndExpand(purchase.getId()).encode().toUri();
        return ResponseEntity.created(uri).body(PurchaseMapper.INSTANCE.mapPurchaseToPurchaseDTO(purchase));
    }


    @ResponseBody
    @GetMapping(path = "/statistics")
    @ApiOperation(value = "Get purchase statistics")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<?> orderItemStatistic() {
        List<OrderItem> list = orderItemService.orderItemStatistic();
        return ResponseEntity.ok(PurchaseStatisticMapper.INSTANCE.mapOrderItemListToPurchaseStatisticDTOList(list));
    }
}
