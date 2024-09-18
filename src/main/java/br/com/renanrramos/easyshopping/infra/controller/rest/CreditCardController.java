/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import java.net.URI;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.delegate.CreditCardDelegate;
import br.com.renanrramos.easyshopping.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CreditCardForm;
import br.com.renanrramos.easyshopping.service.impl.CreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/credit-cards", produces = "application/json")
@Api(tags = "CreditCardEntity")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CreditCardController {

	private final AuthenticationService authenticationService;

	private final CreditCardDelegate creditCardDelegate;

	private URI uri;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new Credit Card")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<CreditCardDTO> saveCreditCard(@Valid @RequestBody CreditCardForm creditCardForm) {;
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
				creditCardDelegate.findCreditCardByCustomerId(pageNumber, pageSize,
						sortBy, authenticationService.getAuthentication().getName());
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
			@RequestBody CreditCardForm creditCardForm) {
		final CreditCardDTO creditCardDto = creditCardDelegate.updateCreditCard(creditCardForm, creditCardId);
		return ResponseEntity.accepted().location(uri).body(creditCardDto);
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
