/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 30/06/2020
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

import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.model.form.CompanyForm;
import br.com.renanrramos.easyshopping.repository.CompanyRepository;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "companies", produces = "application/json")
public class CompanyController {

	@Autowired
	private CompanyRepository companyRepository;
	
	@ResponseBody
	@PostMapping
	@Transactional
	public ResponseEntity<CompanyDTO> saveCompany(@Valid @RequestBody CompanyForm companyForm) {
		Company company = CompanyForm.converterToCompany(companyForm);
		Company companyCreated = companyRepository.save(company);
		if (companyCreated.getId() != null) {
			return ResponseEntity.ok(CompanyDTO.converterToCompanyDTO(companyCreated));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@ResponseBody
	@GetMapping
	public ResponseEntity<List<CompanyDTO>> getCompanies() {
		List<CompanyDTO> listOfCompanyDTOs = CompanyDTO.converterCompanyListToCompanyDTOList(companyRepository.findAll());
		if (listOfCompanyDTOs.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listOfCompanyDTOs);
	}
	
	@ResponseBody
	@GetMapping(path = "/{id}")
	public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable("id") Long companyId) {
		Optional<Company> company = companyRepository.findById(companyId);
		if (company.isPresent()) {
			return ResponseEntity.ok(CompanyDTO.converterToCompanyDTO(company.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	@ResponseBody
	@PutMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<CompanyDTO> updateCompany(@PathVariable("id") Long companyId, @Valid @RequestBody CompanyForm companyForm) {
		Optional<Company> companyOptional = companyRepository.findById(companyId);
		if(companyOptional.isPresent()) {
			Company company = CompanyForm.converterToCompany(companyForm);
			company.setId(companyId);
			CompanyDTO updatedCompanyDTO = CompanyDTO.converterToCompanyDTO((companyRepository.save(company)));
			return ResponseEntity.ok(updatedCompanyDTO);
		}
		return ResponseEntity.notFound().build();
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<CompanyDTO> removeCompany(@PathVariable("id") Long companyId) {
		Optional<Company> companyOptional = companyRepository.findById(companyId);
		if (companyOptional.isPresent()) {
			companyRepository.deleteById(companyId);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
