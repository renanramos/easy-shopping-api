/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 30/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.constants.messages.ConstantsValues;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CompanyDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CompanyForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.infra.delegate.CompanyDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @author renan.ramos
 *
 */
@RestController
@RequestMapping(path = "api/companies", produces = "application/json")
@Api(tags = "Companies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyDelegate companyDelegate;

	@ResponseBody
	@PostMapping
	@Transactional
	@ApiOperation(value = "Save company information")
	@RolesAllowed({ "easy-shopping-admin", "easy-shopping-user" })
	public ResponseEntity<CompanyDTO> saveCompany(@Valid @RequestBody CompanyForm companyForm) throws EasyShoppingException {
		return ResponseEntity.status(HttpStatus.CREATED).body(companyDelegate.saveCompany(companyForm));
	}

	@ResponseBody
	@GetMapping
	@ApiOperation(value = "Get all companies")
	public ResponseEntity<PageResponse<CompanyDTO>> getCompanies(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = ConstantsValues.DEFAULT_SORT_VALUE) String sortBy) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(companyDelegate.findCompanies(new ParametersRequest(pageNumber, pageSize, sortBy), name));
	}

	@ResponseBody
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Get a company by id")
	public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable("id") String companyId) {
		return ResponseEntity.status(HttpStatus.OK).body(companyDelegate.findCompanyByTokenId(companyId));
	}

	@ResponseBody
	@PatchMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Update a company")
	@RolesAllowed({ "easy-shopping-admin", "easy-shopping-user" })
	public ResponseEntity<CompanyDTO> updateCompany(@PathVariable("id") Long companyId,
			@Valid @RequestBody CompanyForm companyForm) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(companyDelegate.updateCompany(companyForm, companyId));
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}")
	@Transactional
	@ApiOperation(value = "Remove a company")
	@RolesAllowed({ "COMPANY", "ADMINISTRATOR", "easy-shopping-user" })
	public ResponseEntity<CompanyDTO> removeCompany(@PathVariable("id") Long companyId) {
		companyDelegate.removeCompany(companyId);
		return ResponseEntity.ok().build();
	}
}