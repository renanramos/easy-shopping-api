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
import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.model.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.model.form.AdministratorForm;
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
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	private URI uri;

	@ResponseBody
	@PostMapping(path = "/register")
	@Transactional
	@ApiOperation(value = "Save a new administrator")
	public ResponseEntity<AdministratorDTO> saveAdministrator(@Valid @RequestBody AdministratorForm administratorForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {
		Administrator administrator = AdministratorForm.converterAdministratorFormToAdministrator(administratorForm);

		administrator.setProfile(Profile.ADMINISTRATOR);

		Administrator administratorCreated = administratorService.save(administrator);
		if (administratorCreated.getId() != null) {
			uri = uriBuilder.path("/admin/{id}").buildAndExpand(administratorCreated.getId()).encode().toUri();
			return ResponseEntity.created(uri).body(AdministratorDTO.converterAdministratorToAdministratorDTO(administratorCreated));
		}
		return ResponseEntity.noContent().build();
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all administrators")
	@RolesAllowed({"ADMINISTRATOR", "easy-shopping-admin"})
	public ResponseEntity<List<AdministratorDTO>> getAdministrators(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber, 
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
		Pageable page = new PageableFactory()
				.withPage(pageNumber)
				.withSize(pageSize)
				.withSort(sortBy)
				.buildPageable();
		List<Administrator> administrators =
				(name == null) ?
						administratorService.findAllPageable(page, null) :
						administratorService.searchAdministratorByName(name);
		return ResponseEntity.ok(AdministratorDTO.converterAdministratorListToAdministratorDTO(administrators)); 
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get an administrator by id")
	@RolesAllowed("easy-shopping-admin")
	public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable("id") Long administratorId) {		
		if (administratorId == null) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.INVALID_ID);
		}

		Optional<Administrator> administratorOptional = administratorService.findById(administratorId);

		if (!administratorOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ADMINISTRATOR_NOT_FOUND);
		}

		return ResponseEntity.ok(AdministratorDTO.converterAdministratorToAdministratorDTO(administratorOptional.get()));
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

		Optional<Administrator> administratorOptional = administratorService.findById(administratorId);

		if (!administratorOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ACCOUNT_NOT_FOUND);
		}
		
		Administrator administrator = AdministratorForm.converterAdministratorFormUpdateToAmdinistrator(administratorForm, administratorOptional.get());
		administrator.setId(administratorId);
		AdministratorDTO administratorUpdated = AdministratorDTO.converterAdministratorToAdministratorDTO(administratorService.save(administrator));
		uri = uriBuilder.path("/admin/{id}").buildAndExpand(administrator.getId()).encode().toUri();

		return ResponseEntity.accepted().location(uri).body(administratorUpdated);
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

		Optional<Administrator> administratorOptional = administratorService.findById(administratorId);

		if (!administratorOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ACCOUNT_NOT_FOUND);
		}
		
		administratorService.remove(administratorId);
		return ResponseEntity.ok().build();
	}
}


















