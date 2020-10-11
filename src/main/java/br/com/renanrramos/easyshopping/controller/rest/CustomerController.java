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

import br.com.renanrramos.easyshopping.config.util.EasyShoppingUtils;
import br.com.renanrramos.easyshopping.config.util.JwtTokenUtil;
import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.model.form.CustomerForm;
import br.com.renanrramos.easyshopping.service.impl.CustomerService;
import br.com.renanrramos.easyshopping.service.impl.MailService;
import br.com.renanrramos.easyshopping.service.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/customers", produces = "application/json")
@Api(tags = "Customers")
@CrossOrigin(origins = "*")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@Autowired
	private EasyShoppingUtils easyShoppingUtils;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private MailService mailService;

	private URI uri;
	
	@ResponseBody
	@PostMapping(path = "/register")
	@Transactional
	@ApiOperation(value = "Save a new customer")
	public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerForm customerForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {
		
		Customer customer = CustomerForm.converterCustomerFormToCustomer(customerForm);

		Optional<List<Customer>> customerByCpf = customerService.findCustomerByCpf(customer.getCpf()); 
		
		if (customerByCpf.isPresent() && !customerByCpf.get().isEmpty()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CPF_ALREADY_EXIST);
		}

		if (userService.isUserEmailAlreadyInUse(customer.getEmail(), null)) {
			throw new EasyShoppingException(ExceptionMessagesConstants.EMAIL_ALREADY_EXIST);
		}

		String password = easyShoppingUtils.encodePassword(customer.getPassword());
		customer.setPassword(password);
		customer.setProfile(Profile.CUSTOMER);

		Customer customerCreated = customerService.save(customer);
		if (customerCreated.getId() != null) {
			String token = jwtTokenUtil.generateToken(customerCreated);
			mailService.sendEmail(token, customerCreated);
			uri = uriBuilder.path("/customers/{id}").buildAndExpand(customerCreated.getId()).encode().toUri();
			return ResponseEntity.created(uri).body(CustomerDTO.converterToCustomerDTO(customer));
		}

		return ResponseEntity.badRequest().build();
	}
	
	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all customers")
	public ResponseEntity<List<CustomerDTO>> getCustomers(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber, 
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
		Pageable page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();
		List<Customer> customers = (name == null) ? 
				customerService.findAllPageable(page, null) :
				customerService.findCustomerByName(page, name);
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
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update a customer")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long customerId, @RequestBody CustomerForm customerForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {

		Optional<Customer> currentCustomer = customerService.findById(customerId);

		if (!currentCustomer.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
		}

		Customer customerFormConverted = CustomerForm.converterCustomerFormUpdateToCustomer(customerForm, currentCustomer.get());

		Optional<List<Customer>> customerByCpf = customerService.findCustomerByCpf(customerFormConverted.getCpf()); 
		
		if (customerByCpf.isPresent() && customerByCpf.get().size() > 1) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CPF_ALREADY_EXIST);
		}

		if (userService.isUserEmailAlreadyInUse(customerFormConverted.getEmail(), customerId)) {
			throw new EasyShoppingException(ExceptionMessagesConstants.EMAIL_ALREADY_EXIST);
		}

		customerFormConverted.setId(customerId);
		CustomerDTO customerUpdatedDTO = CustomerDTO.converterToCustomerDTO(customerService.save(customerFormConverted));
		uri = uriBuilder.path("/customers/{id}").buildAndExpand(customerId).encode().toUri();
		return ResponseEntity.accepted().location(uri).body(customerUpdatedDTO);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Remove a customer")
	public ResponseEntity<CustomerDTO> removeCustomer(@PathVariable("id") Long customerId) {
		Optional<Customer> customerToRemove = customerService.findById(customerId);
		if (!customerToRemove.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
		}
		customerService.remove(customerId);
		return ResponseEntity.ok().build();
	}
}
