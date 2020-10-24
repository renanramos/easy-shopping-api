/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.model.form.CreditCardForm;
import br.com.renanrramos.easyshopping.service.impl.CreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/credit-cards", produces = "application/json")
@Api(tags = "CreditCard")
@CrossOrigin(origins = "*")
public class CreditCardController {

	@Autowired
	private CreditCardService creditCardService;
	
	private URI uri;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new Credit Card")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<CreditCardDTO> saveCreditCard(@Valid @RequestBody CreditCardForm creditCardForm, UriComponentsBuilder uriBuilder, Principal principal) {

		CreditCard creditCard = CreditCardForm.converterCreditCardFormToCreditCard(creditCardForm);
		creditCard.setCustomerId(principal.getName());
		creditCard = creditCardService.save(creditCard);
		uri = uriBuilder.path("/credit-cards/{id}").buildAndExpand(creditCard.getId()).encode().toUri();

		return ResponseEntity.created(uri).body(CreditCardDTO.converterCreditCardToCreditCardDTO(creditCard));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all credit cards")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<List<CreditCardDTO>> getCreditCards(
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber, 
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy,
            Principal principal) {
		Pageable page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();
		List<CreditCard> creditCards = creditCardService.findAllPageable(page, principal.getName());
		return ResponseEntity.ok(CreditCardDTO.converterCreditCardListToCreditCardDTOList(creditCards));
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get a credit card by id")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<CreditCardDTO> getCreditCardById(@PathVariable("id") Long creditCardId) {
		Optional<CreditCard> creditCardOptional = creditCardService.findById(creditCardId);

		if (!creditCardOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
		}

		CreditCard creditCard = creditCardOptional.get();
		return ResponseEntity.ok(CreditCardDTO.converterCreditCardToCreditCardDTO(creditCard));
	}

	@ResponseBody
	@PatchMapping(path = "/{id}")
	@ApiOperation(value = "Update a credit card")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<CreditCardDTO> updateCreditCard(@PathVariable("id") Long creditCardId, @RequestBody CreditCardForm creditCardForm, UriComponentsBuilder uriBuilder,
			Principal principal) {

		Optional<CreditCard> currentCreditCard = creditCardService.findById(creditCardId);

		if (!currentCreditCard.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
		}

		CreditCard creditCard = CreditCardForm.converterCreditCardFormUpdateToCreditCard(creditCardForm, currentCreditCard.get());
		creditCard.setId(creditCardId);
		creditCard.setCustomerId(principal.getName());
		creditCard = creditCardService.save(creditCard);
		uri = uriBuilder.path("/credit-cards/{id}").buildAndExpand(creditCard.getId()).encode().toUri();

		return ResponseEntity.accepted().location(uri).body(CreditCardDTO.converterCreditCardToCreditCardDTO(creditCard));
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove a credit card")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<CreditCardDTO> removeCreditCard(@PathVariable("id") Long creditCardId) {
		Optional<CreditCard> creditCardOptional = creditCardService.findById(creditCardId);

		if (!creditCardOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
		}

		creditCardService.remove(creditCardId);
		return ResponseEntity.ok().build();
	}
}
