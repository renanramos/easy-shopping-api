/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 12/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.delegate.AddressDelegate;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AddressDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AddressForm;
import br.com.renanrramos.easyshopping.service.impl.AddressService;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@Controller
@RequestMapping(path = "api/addresses", produces = "application/json")
@Api(tags = "Address")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AddressController {

	private final AddressService addressService;

	private final AddressDelegate addressDelegate;

	private URI uri;

	@Autowired
	private AuthenticationServiceImpl authenticationServiceImpl;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new address")
	@RolesAllowed({"CUSTOMER", "easy-shopping-user", "app-customer"})
	public ResponseEntity<AddressDTO> saveAddress(@Valid @RequestBody AddressForm addressForm,
			UriComponentsBuilder uriBuilder) {
		final AddressDTO addressDTO = addressDelegate.saveAddress(addressForm);
		uri = uriBuilder.path("/addresses/{id}").buildAndExpand(addressDTO.getId()).encode().toUri();
		return ResponseEntity.status(HttpStatus.CREATED).body(addressDTO);
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all addresses")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<PageResponse<AddressDTO>> getAddresses(
			@RequestParam(required = false) Long customerId,
			@RequestParam(required = false) String streetName,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(addressDelegate.findAddresses(pageNumber, pageSize, sortBy, streetName));
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get an address by id")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") Long addressId) {
		return ResponseEntity.ok(addressDelegate.findAddressById(addressId));
	}

	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update an address")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<AddressDTO> updateAddress(@PathVariable("id") Long addressId,
			@RequestBody AddressForm addressForm, UriComponentsBuilder uriBuilder) {

		Optional<Address> currentAddress = addressService.findById(addressId);

		if (!currentAddress.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
		}

		Address address = currentAddress.get();

		AddressMapper.INSTANCE.mapAddressFormToUpdateAddress(currentAddress.get(), addressForm);
		address.setId(addressId);
		address.setCustomerId(authenticationServiceImpl.getName());
		address = addressService.save(address);
		uri = uriBuilder.path("/addresses/{id}").buildAndExpand(address.getId()).encode().toUri();

		return ResponseEntity.accepted().location(uri).body(AddressMapper.INSTANCE.mapAddressToAddressDTO(address));
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove an address")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<AddressDTO> removeAddress(@PathVariable("id") Long addressId) {
		Optional<Address> addressOptional = addressService.findById(addressId);

		if (!addressOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ADDRESS_NOT_FOUND);
		}

		addressService.remove(addressId);
		return ResponseEntity.ok().build();
	}

}
