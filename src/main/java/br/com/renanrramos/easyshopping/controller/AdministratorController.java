/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.model.dto.AdministratorDTO;
import br.com.renanrramos.easyshopping.model.form.AdministratorForm;
import br.com.renanrramos.easyshopping.repository.AdministratorRepository;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "admin", produces = "application/json")
public class AdministratorController {

	@Autowired
	private AdministratorRepository administratorRepository;
	
	@ResponseBody
	@PostMapping
	@Transactional
	public ResponseEntity<AdministratorDTO> saveAdministrator(@Valid @RequestBody AdministratorForm administratorForm) {
		Administrator administrator = AdministratorForm.converterAdministratorFormToAdministrator(administratorForm);
		Administrator administratorCreated = administratorRepository.save(administrator);
		if (administratorCreated.getId() != null) {
			return ResponseEntity.ok(AdministratorDTO.converterAdministratorToAdministratorDTO(administratorCreated));
		}
		return ResponseEntity.noContent().build();
	}

	@ResponseBody
	@GetMapping
	public ResponseEntity<List<AdministratorDTO>> getAdministrators() {
		List<AdministratorDTO> administrators = AdministratorDTO.converterAdministratorListToAdministratorDTO(administratorRepository.findAll());
		if (administrators.isEmpty()) {
			return ResponseEntity.noContent().build();			
		}
		return ResponseEntity.ok(administrators); 
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	public ResponseEntity<AdministratorDTO> getAdministratorByID(@PathVariable("id") Long administratorId) {
		Optional<Administrator> administratorOptional = administratorRepository.findById(administratorId);
		if (administratorOptional.isPresent()) {
			return ResponseEntity.ok(AdministratorDTO.converterAdministratorToAdministratorDTO(administratorOptional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@ResponseBody
	@PutMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<AdministratorDTO> updateAdministrator(@PathVariable("id") Long administratorId, @RequestBody AdministratorForm administratorForm) {
		Optional<Administrator> administratorOptional = administratorRepository.findById(administratorId);
		if (administratorOptional.isPresent()) {
			Administrator administrator = AdministratorForm.converterAdministratorFormToAdministrator(administratorForm);
			administrator.setId(administratorId);
			AdministratorDTO administratorUpdated = AdministratorDTO.converterAdministratorToAdministratorDTO(administratorRepository.save(administrator));
			return ResponseEntity.ok(administratorUpdated);
		}
		return ResponseEntity.notFound().build();
	}
	
	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<?> removeAdministrator(@PathVariable("id") Long administratorId) {
		Optional<Administrator> administratorOptional = administratorRepository.findById(administratorId);
		if (administratorOptional.isPresent()) {
			administratorRepository.deleteById(administratorId);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}


















