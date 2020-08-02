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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(path = "creditCards", produces = "application/json")
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
			throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
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
			return ResponseEntity.notFound().build();
		}
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all credit cards")
	public ResponseEntity<List<CreditCardDTO>> getCreditCards() {
		List<CreditCard> creditCards = creditCardService.findAll();
		return ResponseEntity.ok(CreditCardDTO.converterCreditCardListToCreditCardDTOList(creditCards));
	}
	
}
