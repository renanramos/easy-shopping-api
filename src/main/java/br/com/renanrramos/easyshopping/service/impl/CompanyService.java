/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.repository.CompanyRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class CompanyService implements CommonService<Company>{

	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public Company save(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public List<Company> findAll(Pageable page) {
		Page<Company> pagedResult = companyRepository.findAll(page); 
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	public List<Company> findAllCompanies() {
		return companyRepository.findAll();
	}
	
	@Override
	public Optional<Company> findById(Long companyId) {
		return companyRepository.findById(companyId);
	}

	@Override
	public Company update(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public void remove(Long companyId) {
		companyRepository.deleteById(companyId);
	}

	public boolean isRegisteredNumberInvalid(String registeredNumber) {
		Company company = companyRepository.findTopCompanyByRegisteredNumber(registeredNumber);
		return Optional.ofNullable(company).isPresent();
	}

	@Override
	public List<Company> findAllPageable(Pageable page, Long id) {
		return new ArrayList<>();
	}

	public List<Company> findCompanyByName(Pageable page, String name) {
		Page<Company> pagedResult = companyRepository.getCompanyByNameRegisteredNumberOrEmail(page, name); 
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}
}
