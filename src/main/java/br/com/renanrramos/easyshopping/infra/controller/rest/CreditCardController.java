/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CreditCardForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.delegate.CreditCardDelegate;
import br.com.renanrramos.easyshopping.service.BaseAuthenticationService;
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

/**
 * @author renan.ramos
 */
@RestController
@RequestMapping(path = "api/credit-cards", produces = "application/json")
@Api(tags = "CreditCardEntity")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CreditCardController {

    private final BaseAuthenticationService baseAuthenticationService;

    private final CreditCardDelegate creditCardDelegate;

    @ResponseBody
    @PostMapping
    @Transactional
    @ApiOperation(value = "Save a new Credit Card")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<CreditCardDTO> saveCreditCard(@Valid @RequestBody CreditCardForm creditCardForm) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creditCardDelegate.saveCreditCard(creditCardForm));
    }

    @ResponseBody
    @GetMapping
    @ApiOperation(value = "Get all credit cards")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<PageResponse<CreditCardDTO>> getCreditCards(
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
        final PageResponse<CreditCardDTO> creditCardResponse =
                creditCardDelegate.findCreditCardByCustomerId(new ParametersRequest(pageNumber, pageSize, sortBy),
                        baseAuthenticationService.getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.OK).body(creditCardResponse);
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get a credit card by id")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<CreditCardDTO> getCreditCardById(@PathVariable("id") Long creditCardId) {
        return ResponseEntity.ok(creditCardDelegate.findCreditCardById(creditCardId));
    }

    @ResponseBody
    @PatchMapping(path = "/{id}")
    @ApiOperation(value = "Update a credit card")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<CreditCardDTO> updateCreditCard(@PathVariable("id") Long creditCardId,
                                                          @RequestBody CreditCardForm creditCardForm, UriComponentsBuilder uriBuilder) {
        final CreditCardDTO creditCardDto = creditCardDelegate.updateCreditCard(creditCardForm, creditCardId);
        return ResponseEntity.accepted().location(uriBuilder.path("/credit-cards/{id}")
                .buildAndExpand(creditCardDto.getId())
                .encode()
                .toUri()).body(creditCardDto);
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    @Transactional
    @ApiOperation(value = "Remove a credit card")
    @RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
    public ResponseEntity<CreditCardDTO> removeCreditCard(@PathVariable("id") Long creditCardId) {
        creditCardDelegate.remove(creditCardId);
        return ResponseEntity.ok().build();
    }
}
