/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
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

import br.com.renanrramos.easyshopping.core.domain.Administrator;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.delegate.AdministratorDelegate;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.AdministratorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.AdministratorEntity;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.AdministratorForm;
import br.com.renanrramos.easyshopping.service.impl.AdministratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "/api/admin", produces = "application/json")
@Api(tags = "Administrators")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	private final AdministratorDelegate administratorDelegate;

	private URI uri;

	@ResponseBody
	@PostMapping(path = "/register")
	@Transactional
	@ApiOperation(value = "Save a new administrator")
	public ResponseEntity<AdministratorDTO> saveAdministrator(@Valid @RequestBody AdministratorForm administratorForm) {
		return ResponseEntity.status(HttpStatus.CREATED).body(administratorDelegate.save(administratorForm));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all administrators")
	@RolesAllowed({"ADMINISTRATOR", "easy-shopping-admin"})
	public ResponseEntity<PageResponse<AdministratorDTO>> getAdministrators(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber, 
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(administratorDelegate
						.findAdministrators(new ParametersRequest(pageNumber, pageSize, sortBy), name));
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get an administrator by id")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable("id") Long administratorId) {		
		if (administratorId == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.INVALID_ID);
		}

		Optional<AdministratorEntity> administratorOptional = administratorService.findById(administratorId);

		if (!administratorOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ADMINISTRATOR_NOT_FOUND);
		}

//		return ResponseEntity.ok(AdministratorMapper.INSTANCE.mapAdministratorToAdministratorDTO(administratorOptional.get()));
		return ResponseEntity.ok().build();
	}
	
	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update an administrator")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<AdministratorDTO> updateAdministrator(@PathVariable("id") Long administratorId, @RequestBody AdministratorForm administratorForm, UriComponentsBuilder uriBuilder) {
		if (administratorId == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.INVALID_ID);
		}

//		Optional<Administrator> administratorOptional = administratorService.findById(administratorId);
//
//		if (!administratorOptional.isPresent()) {
//			throw new EntityNotFoundException(ExceptionMessagesConstants.ACCOUNT_NOT_FOUND);
//		}
//		Administrator administrator = administratorOptional.get();
//		AdministratorMapper.INSTANCE.mapAdministratorFormToUpdateAdministrator(administrator, administratorForm);
//		administrator.setId(administratorId);
//		AdministratorDTO administratorUpdated = AdministratorMapper.INSTANCE.mapAdministratorToAdministratorDTO(administratorService.save(administratorEntity));
//		uri = uriBuilder.path("/admin/{id}").buildAndExpand(administrator.getId()).encode().toUri();

//		return ResponseEntity.accepted().location(uri).body(administratorUpdated);
		return ResponseEntity.ok().build();
	}
	
	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove an administrator")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<AdministratorDTO> removeAdministrator(@PathVariable("id") Long administratorId) {

		if (administratorId == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.INVALID_ID);
		}

		Optional<AdministratorEntity> administratorOptional = administratorService.findById(administratorId);

		if (!administratorOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ACCOUNT_NOT_FOUND);
		}
		
		administratorService.remove(administratorId);
		return ResponseEntity.ok().build();
	}
}


















