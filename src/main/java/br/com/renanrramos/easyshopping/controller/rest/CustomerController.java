/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 25/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.model.form.CustomerForm;
import br.com.renanrramos.easyshopping.service.impl.CustomerService;
import br.com.renanrramos.easyshopping.service.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@CrossOrigin
@RequestMapping(path = "api/customers", produces = "application/json")
@Api(tags = "Customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;
	
	private URI uri;
	
	@ResponseBody
	@Transactional
	@PostMapping
	@ApiOperation(value = "Save a new customer")
	public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerForm customerForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {
		Customer customer = CustomerForm.converterCustomerFormToCustomer(customerForm);

		if (customerService.isCpfInvalid(customer.getCpf())) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CPF_ALREADY_EXIST);
		}

		if (userService.isUserEmailInvalid(customer.getEmail())) {
			throw new EasyShoppingException(ExceptionMessagesConstants.EMAIL_ALREADY_EXIST);
		}

		Customer customerCreated = customerService.save(customer);
		if (customerCreated.getId() != null) {
			uri = uriBuilder.path("/customers/{id}").buildAndExpand(customerCreated.getId()).encode().toUri();
			return ResponseEntity.created(uri).body(CustomerDTO.converterToCustomerDTO(customer));
		}

		return ResponseEntity.badRequest().build();
	}
	
	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all customers")
	public ResponseEntity<List<CustomerDTO>> getCustomers(
			@RequestParam(defaultValue = "0") Integer pageNumber, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {		
		List<Customer> customers = customerService.findAllPageable(pageNumber, pageSize, sortBy);
		return ResponseEntity.ok(CustomerDTO.converterCustomerListToCustomerDTOList(customers));
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get a customer by id")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long customerId) {
		Optional<Customer> customer = customerService.findById(customerId);
		if (customer.isPresent()) {
			return ResponseEntity.ok(CustomerDTO.converterToCustomerDTO(customer.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@ResponseBody
	@PutMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update a customer")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long customerId, @RequestBody CustomerForm customerForm, UriComponentsBuilder uriBuilder) {
		Optional<Customer> customerToUpdate = customerService.findById(customerId);
		if(customerToUpdate.isPresent()) {			
			Customer customer = CustomerForm.converterCustomerFormToCustomer(customerForm);
			customer.setId(customerId);
			CustomerDTO customerUpdatedDTO = CustomerDTO.converterToCustomerDTO(customerService.save(customer));
			uri = uriBuilder.path("/customers/{id}").buildAndExpand(customerId).encode().toUri();
			return ResponseEntity.accepted().location(uri).body(customerUpdatedDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Remove a customer")
	public ResponseEntity<?> removeCustomer(@PathVariable("id") Long customerId) {
		Optional<Customer> customerToRemove = customerService.findById(customerId);
		if (customerToRemove.isPresent()) {
			customerService.remove(customerId);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}	
}
