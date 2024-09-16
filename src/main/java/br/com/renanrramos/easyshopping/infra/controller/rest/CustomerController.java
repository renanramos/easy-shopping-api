/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 25/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CustomerMapper;
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
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.infra.controller.rest.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.infra.controller.rest.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.rest.form.CustomerForm;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.CustomerService;
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

	private URI uri;

	@Autowired
	private AuthenticationServiceImpl authenticationServiceImpl;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save customer information")
	@RolesAllowed({ "easy-shopping-admin", "easy-shopping-user" })
	public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerForm customerForm,
			UriComponentsBuilder uriBuilder) throws EasyShoppingException {

		Customer customer = CustomerMapper.INSTANCE.mapCustomerFormToCustomer(customerForm);

		Optional<List<Customer>> customerByCpf = customerService.findCustomerByCpf(customer.getCpf());

		if (customerByCpf.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CPF_ALREADY_EXIST);
		}

		customer.setTokenId(authenticationServiceImpl.getName());
		customer.setSync(true);

		Customer customerCreated = customerService.save(customer);
		if (customerCreated.getId() != null) {
			uri = uriBuilder.path("/customers/{id}").buildAndExpand(customerCreated.getId()).encode().toUri();
			return ResponseEntity.created(uri).body(CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customer));
		}

		return ResponseEntity.badRequest().build();
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all customers")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
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
		List<Customer> customers = (name == null || name.isEmpty()) ?
				customerService.findAllPageable(page, null) :
					customerService.findCustomerByName(page, name);
				return ResponseEntity.ok(CustomerMapper.INSTANCE.mapCustomerListToCustomerDTOList(customers));
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get a customer by id")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") String tokenId) {
		Optional<Customer> customer = customerService.findCustomerByTokenId(tokenId);
        return customer.map(value -> ResponseEntity
						.ok(CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(value)))
						 .orElseGet(() -> ResponseEntity.notFound().build());
    }

	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update a customer")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") String customerId,
			@RequestBody CustomerForm customerForm, UriComponentsBuilder uriBuilder, Principal principal)
					throws EasyShoppingException {

		Optional<Customer> currentCustomer = customerService.findCustomerByTokenId(customerId);

		if (!currentCustomer.isPresent()) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
		}

		Customer customer = currentCustomer.get();
		CustomerMapper.INSTANCE.mapCustomerFormToUpdateCustomer(customer, customerForm);

		Optional<List<Customer>> customerByCpf = customerService.findCustomerByCpf(customer.getCpf());

		if (customerByCpf.isPresent() && customerByCpf.get().size() > 1) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CPF_ALREADY_EXIST);
		}

		customer.setId(currentCustomer.get().getId());
		customer.setTokenId(customerId);
		customer.setSync(true);
		CustomerDTO customerUpdatedDTO = CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customerService.update(customer));
		uri = uriBuilder.path("/customers/{id}").buildAndExpand(customerId).encode().toUri();
		return ResponseEntity.accepted().location(uri).body(customerUpdatedDTO);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Remove a customer")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<CustomerDTO> removeCustomer(@PathVariable("id") Long customerId) {
		Optional<Customer> customerToRemove = customerService.findById(customerId);
		if (!customerToRemove.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
		}
		customerService.remove(customerId);
		return ResponseEntity.ok().build();
	}
}
