/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.renanrramos.easyshopping.interfaceadapter.mapper.StoreMapper;
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
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.dto.StoreDTO;
import br.com.renanrramos.easyshopping.model.form.StoreForm;
import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/stores", produces = "application/json")
@Api(tags = "Stores")
@CrossOrigin(origins = "*")
public class StoreController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private AuthenticationServiceImpl authenticationServiceImpl;

	private URI uri;

	@ResponseBody
	@Transactional
	@PostMapping
	@ApiOperation(value = "Save a new store")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<StoreDTO> saveStore(@Valid @RequestBody StoreForm storeForm, UriComponentsBuilder uriBuilder)
			throws EasyShoppingException {

		if (storeService.isRegisteredNumberInvalid(storeForm.getRegisteredNumber())) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CNPJ_ALREADY_EXIST);
		}

		Store store = StoreMapper.INSTANCE.mapStoreFormToStore(storeForm);
		store.setTokenId(authenticationServiceImpl.getName());
		store = storeService.save(store);
		uri = uriBuilder.path("/stores/{id}").buildAndExpand(store.getId()).encode().toUri();
		return ResponseEntity.created(uri).body(StoreMapper.INSTANCE.mapStoreToStoreDTO(store));
	}

	@ResponseBody
	@GetMapping("/company")
	@ApiOperation(value = "Get all stores of the logged in company")
	public ResponseEntity<List<StoreDTO>> getCompanyOwnStores(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {

		Pageable page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();

		List<Store> stores = storeService.findAllPageable(page, authenticationServiceImpl.getName(), name);
		return ResponseEntity.ok(StoreMapper.INSTANCE.mapStoreListToStoreDTOList(stores));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all stores")
	public ResponseEntity<List<StoreDTO>> getStores(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
		Pageable page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();

		List<Store> stores = (name == null || name.isEmpty()) ?
				storeService.findAll(page) :
					storeService.findStoreByName(page, name);
				return ResponseEntity.ok(StoreMapper.INSTANCE.mapStoreListToStoreDTOList(stores));
	}

	@ResponseBody
	@GetMapping("/{id}")
	@ApiOperation(value = "Get a store by id")
	public ResponseEntity<StoreDTO> getStoreById(@PathVariable("id") Long storeId) {
		Optional<Store> storeOptional = storeService.findById(storeId);
		if (storeOptional.isPresent()) {
			StoreDTO storeDto = StoreMapper.INSTANCE.mapStoreToStoreDTO(storeOptional.get());
			return ResponseEntity.ok(storeDto);
		}
		return ResponseEntity.notFound().build();
	}

	@ResponseBody
	@PatchMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Update a store")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<StoreDTO> updateStore(@PathVariable("id") Long storeId, @RequestBody StoreForm storeForm,
			UriComponentsBuilder uriBuilder) throws EasyShoppingException {

		Optional<Store> currentStore = storeService.findById(storeId);

		if (!currentStore.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.STORE_NOT_FOUND);
		}

		Store store = currentStore.get();
		StoreMapper.INSTANCE.mapStoreFormToUpdateStore(store, storeForm);
		store.setTokenId(authenticationServiceImpl.getName());
		store.setId(storeId);
		StoreDTO storeUpdatedDTO = StoreMapper.INSTANCE.mapStoreToStoreDTO(storeService.update(store));
		uri = uriBuilder.path("/stores/{id}").buildAndExpand(storeId).encode().toUri();

		return ResponseEntity.accepted().location(uri).body(storeUpdatedDTO);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Remove a store")
	@RolesAllowed({"easy-shopping-admin", "easy-shopping-user"})
	public ResponseEntity<StoreDTO> removeStore(@PathVariable("id") Long storeId) {
		Optional<Store> storeOptional = storeService.findById(storeId);
		if (!storeOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.STORE_NOT_FOUND);
		}
		storeService.remove(storeId);
		return ResponseEntity.ok().build();
	}
}
