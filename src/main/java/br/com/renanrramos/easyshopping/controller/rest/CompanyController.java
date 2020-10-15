/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 30/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityExistsException;
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

import br.com.renanrramos.easyshopping.config.util.EasyShoppingUtils;
import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.factory.PageableFactory;
import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.model.form.CompanyForm;
import br.com.renanrramos.easyshopping.service.impl.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/companies", produces = "application/json")
@Api(tags = "Companies")
@CrossOrigin(origins = "*")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private EasyShoppingUtils easyShoppingUtils;

	private URI uri;
	
	@ResponseBody
	@PostMapping(path = "/register")
	@Transactional
	@ApiOperation(value = "Save a new company")
	public ResponseEntity<CompanyDTO> saveCompany(@Valid @RequestBody CompanyForm companyForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {
		Company company = CompanyForm.converterCompanyFormToCompany(companyForm);

		if (companyService.isRegisteredNumberInvalid(company.getRegisteredNumber())) {
			throw new EasyShoppingException(ExceptionMessagesConstants.CNPJ_ALREADY_EXIST);
		}

		String password = easyShoppingUtils.encodePassword(company.getPassword());

		company.setPassword(password);
		company.setProfile(Profile.COMPANY);

		Company companyCreated = companyService.save(company);
		if (companyCreated.getId() != null) {
			uri = uriBuilder.path("/companies/{id}").buildAndExpand(companyCreated.getId()).encode().toUri();			
			return ResponseEntity.created(uri).body(CompanyDTO.converterToCompanyDTO(companyCreated));
		}

		return ResponseEntity.badRequest().build();
	}
	
	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all companies")
	public ResponseEntity<List<CompanyDTO>> getCompanies(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber, 
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
			Pageable page = new PageableFactory()
					.withPage(pageNumber)
					.withSize(pageSize)
					.withSort(sortBy)
					.buildPageable();
			List<CompanyDTO> listOfCompanyDTOs = (name == null) ?
					CompanyDTO.converterCompanyListToCompanyDTOList(companyService.findAll(page)) :
						CompanyDTO.converterCompanyListToCompanyDTOList(companyService.findCompanyByName(page, name));
		
		return ResponseEntity.ok(listOfCompanyDTOs);
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get a company by id")
	public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable("id") Long companyId) {
		Optional<Company> company = companyService.findById(companyId);
		if (!company.isPresent()) {
			throw new EntityExistsException(ExceptionMessagesConstants.COMPANY_NOT_FOUND);
		}
		return ResponseEntity.ok(CompanyDTO.converterToCompanyDTO(company.get()));
	}
	
	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update a company")
	@RolesAllowed({"COMPANY", "ADMINISTRATOR"})
	public ResponseEntity<CompanyDTO> updateCompany(@PathVariable("id") Long companyId, @Valid @RequestBody CompanyForm companyForm, UriComponentsBuilder uriBuilder) throws EasyShoppingException {
		Optional<Company> currentCompany = companyService.findById(companyId);

		if(!currentCompany.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ACCOUNT_NOT_FOUND);
		}

		Company company = CompanyForm.converterCompanyFormUpdateToCompany(companyForm, currentCompany.get());

		company.setId(companyId);
		CompanyDTO updatedCompanyDTO = CompanyDTO.converterToCompanyDTO((companyService.save(company)));
		uri = uriBuilder.path("/companies/{id}").buildAndExpand(company.getId()).encode().toUri();

		return ResponseEntity.accepted().location(uri).body(updatedCompanyDTO);
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove a company")
	@RolesAllowed({"COMPANY", "ADMINISTRATOR"})
	public ResponseEntity<CompanyDTO> removeCompany(@PathVariable("id") Long companyId) {
		Optional<Company> companyOptional = companyService.findById(companyId);

		if (!companyOptional.isPresent()) {
			throw new EntityNotFoundException(ExceptionMessagesConstants.ACCOUNT_NOT_FOUND);
		}

		companyService.remove(companyId);
		return ResponseEntity.ok().build();
	}
}
