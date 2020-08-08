/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.model.form.CreditCardForm;
import br.com.renanrramos.easyshopping.service.impl.CreditCardService;
import br.com.renanrramos.easyshopping.service.impl.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@CrossOrigin
@RequestMapping(path = "api/credit-cards", produces = "application/json")
@Api(tags = "CreditCard")
public class CreditCardController {

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private CustomerService customerService;
	
	private URI uri;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new Credit Card")
	public ResponseEntity<CreditCardDTO> saveCreditCard(@Valid @RequestBody CreditCardForm creditCardForm, UriComponentsBuilder uriBuilder) {

		if (creditCardForm.getCustomerId() == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Customer> customerOptional = customerService.findById(creditCardForm.getCustomerId()); 

		if (customerOptional.isPresent()) {
			
			Customer customer = customerOptional.get();
			CreditCard creditCard = CreditCardForm.converterCreditCardFormToCreditCard(creditCardForm);
			creditCard.setCustomer(customer);
			creditCard = creditCardService.save(creditCard);
			uri = uriBuilder.path("/creditCards/{id}").buildAndExpand(creditCard.getId()).encode().toUri();

			return ResponseEntity.created(uri).body(CreditCardDTO.converterCreditCardToCreditCardDTO(creditCard));
		} else {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
		}
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all credit cards")
	public ResponseEntity<List<CreditCardDTO>> getCreditCards(
			@RequestParam(required = false) Long customerId,
			@RequestParam(defaultValue = "0") Integer pageNumber, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
		List<CreditCard> creditCards = 
				(customerId == null) ?
				creditCardService.findAllPageable(pageNumber, pageSize, sortBy) :
				creditCardService.findCreditCardByCustomerId(customerId);
		return ResponseEntity.ok(CreditCardDTO.converterCreditCardListToCreditCardDTOList(creditCards));
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get a credit card by id")
	public ResponseEntity<CreditCardDTO> getCreditCardById(@PathVariable("id") Long creditCardId) {
		Optional<CreditCard> creditCardOptional = creditCardService.findById(creditCardId);

		if (creditCardOptional.isPresent()) {
			CreditCard creditCard = creditCardOptional.get();
			return ResponseEntity.ok(CreditCardDTO.converterCreditCardToCreditCardDTO(creditCard));
		}

		return ResponseEntity.notFound().build();
	}

	@ResponseBody
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Update a credit card")
	public ResponseEntity<?> updateCreditCard(@PathVariable("id") Long creditCardId, @RequestBody CreditCardForm creditCardForm, UriComponentsBuilder uriBuilder) {
		
		if (creditCardForm.getCustomerId() == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_ID_NOT_FOUND_ON_REQUEST);
		}

		Optional<Customer> customerOptional = customerService.findById(creditCardForm.getCustomerId());
		Customer customer;
		if (customerOptional.isPresent()) {
			customer = customerOptional.get();
		} else {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
		}

		Optional<CreditCard> creditCardOptional = creditCardService.findById(creditCardId);

		if (creditCardOptional.isPresent()) {
			CreditCard creditCard = CreditCardForm.converterCreditCardFormToCreditCard(creditCardForm);
			creditCard.setId(creditCardId);
			creditCard.setCustomer(customer);
			creditCard = creditCardService.save(creditCard);
			uri = uriBuilder.path("/creditCards/{id}").buildAndExpand(creditCard.getId()).encode().toUri();
			return ResponseEntity.accepted().location(uri).body(CreditCardDTO.converterCreditCardToCreditCardDTO(creditCard));
		} else {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
		}
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove a credit card")
	public ResponseEntity<?> removeCreditCard(@PathVariable("id") Long creditCardId) {
		Optional<CreditCard> creditCardOptional = creditCardService.findById(creditCardId);

		if (creditCardOptional.isPresent()) {
			creditCardService.remove(creditCardId);
			return ResponseEntity.ok().build();
		} else {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
		}
	}
}
