/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 30/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<CompanyDTO> saveCompany(@Valid CompanyForm companyForm) {
		System.out.println(companyForm.toString());
		Company company = CompanyForm.converterToCompany(companyForm);
		System.out.println(company.toString());
		Company companyCreated = companyRepository.save(company);
		if (companyCreated.getId() != null) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
}
