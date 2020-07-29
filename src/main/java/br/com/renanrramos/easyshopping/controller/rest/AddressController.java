/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 12/07/2020
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.dto.AddressDTO;
import br.com.renanrramos.easyshopping.model.form.AddressForm;
import br.com.renanrramos.easyshopping.service.impl.AddressService;
import br.com.renanrramos.easyshopping.service.impl.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@CrossOrigin
@RequestMapping(path = "addresses", produces = "application/json")
@Api(tags = "Address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CustomerService customerService;
	
	private URI uri;
	
	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new address")
	public ResponseEntity<AddressDTO> saveAddress(@Valid @RequestBody AddressForm addressForm, UriComponentsBuilder uriBuilder) {
		
		if (addressForm.getCustomerId() == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
		}

		Optional<Customer> customerOptional = customerService.findById(addressForm.getCustomerId());
		
		if (customerOptional.isPresent()) {
			
			Customer customer = customerOptional.get();
			Address address = AddressForm.convertAddressFormToAddress(addressForm);
			address.setCustomer(customer);
			address = addressService.save(address);
			uri = uriBuilder.path("/addresses/{id}").buildAndExpand(address.getId()).encode().toUri();
			
			return ResponseEntity.created(uri).body(AddressDTO.convertAddressToAddressDTO(address));
			
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all addresses")
	public ResponseEntity<List<AddressDTO>> getAddresses() {
		List<Address> addresses = addressService.findAll();
		return ResponseEntity.ok(AddressDTO.convertAddressListToAddressDTOList(addresses));	
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get an address by id")
	public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") Long addressId) {
		Optional<Address> addressOptional = addressService.findById(addressId);
		
		if(addressOptional.isPresent()) {
			Address address = addressOptional.get();
			return ResponseEntity.ok(AddressDTO.convertAddressToAddressDTO(address));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@ResponseBody
	@PutMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update an address")
	public ResponseEntity<?> updateAddress(@PathVariable("id") Long addressId, @RequestBody AddressForm addressForm, UriComponentsBuilder uriBuilder) {
		
		Optional<Customer> customerOptional = customerService.findById(addressForm.getCustomerId());
		Customer customer;
		if (customerOptional.isPresent()) {
			customer = customerOptional.get();
		} else {
			throw new EntityNotFoundException(ExceptionMessagesConstants.CUSTOMER_NOT_FOUND);
		}

		Optional<Address> addressOptional = addressService.findById(addressId);
		
		if (addressOptional.isPresent()) {
			
			Address address = AddressForm.convertAddressFormToAddress(addressForm);
			address.setId(addressId);
			address.setCustomer(customer);
			address = addressService.save(address);
			uri = uriBuilder.path("/addresses/{id}").buildAndExpand(address.getId()).encode().toUri();
			return ResponseEntity.accepted().location(uri).body(AddressDTO.convertAddressToAddressDTO(address));
		} else {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove an address")
	public ResponseEntity<?> removeAddress(@PathVariable("id") Long addressId) {
		Optional<Address> addressOptional = addressService.findById(addressId);
		
		if (addressOptional.isPresent()) {
			addressService.remove(addressId);
		} else {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
		}
		return ResponseEntity.badRequest().build();
	}

}
