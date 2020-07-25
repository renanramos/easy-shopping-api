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
@CrossOrigin
@RequestMapping(path = "admin", produces = "application/json")
@Api(tags = "Administrators")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	private URI uri;
	
	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save a new administrator")
	public ResponseEntity<AdministratorDTO> saveAdministrator(@Valid @RequestBody AdministratorForm administratorForm, UriComponentsBuilder uriBuilder) {
		Administrator administrator = AdministratorForm.converterAdministratorFormToAdministrator(administratorForm);
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
	public ResponseEntity<List<AdministratorDTO>> getAdministrators() {
		List<AdministratorDTO> administrators = AdministratorDTO.converterAdministratorListToAdministratorDTO(administratorService.findAll());
		if (administrators.isEmpty()) {
			return ResponseEntity.noContent().build();			
		}
		return ResponseEntity.ok(administrators); 
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get an administrator by id")
	public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable("id") Long administratorId) {
		Optional<Administrator> administratorOptional = administratorService.findById(administratorId);
		if (administratorOptional.isPresent()) {
			return ResponseEntity.ok(AdministratorDTO.converterAdministratorToAdministratorDTO(administratorOptional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@ResponseBody
	@PutMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update an administrator")
	public ResponseEntity<AdministratorDTO> updateAdministrator(@PathVariable("id") Long administratorId, @RequestBody AdministratorForm administratorForm, UriComponentsBuilder uriBuilder) {
		Optional<Administrator> administratorOptional = administratorService.findById(administratorId);
		if (administratorOptional.isPresent()) {
			Administrator administrator = AdministratorForm.converterAdministratorFormToAdministrator(administratorForm);
			administrator.setId(administratorId);
			AdministratorDTO administratorUpdated = AdministratorDTO.converterAdministratorToAdministratorDTO(administratorService.save(administrator));
			uri = uriBuilder.path("/admin/{id}").buildAndExpand(administrator.getId()).encode().toUri();
			return ResponseEntity.accepted().location(uri).body(administratorUpdated);
		} else {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ACCOUNT_NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove an administrator")
	public ResponseEntity<?> removeAdministrator(@PathVariable("id") Long administratorId) {
		Optional<Administrator> administratorOptional = administratorService.findById(administratorId);
		if (administratorOptional.isPresent()) {
			administratorService.remove(administratorId);
			return ResponseEntity.ok().build();
		} else {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ACCOUNT_NOT_FOUND);
		}
	}
}


















