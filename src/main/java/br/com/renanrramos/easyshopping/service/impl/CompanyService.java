/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import br.com.renanrramos.easyshopping.core.domain.Company;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.CompanyRepository;
import br.com.renanrramos.easyshopping.model.CompanyEntity;
import br.com.renanrramos.easyshopping.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
//		return companyRepository.save(company);
		return null;
	}

	@Override
	public List<Company> findAll(Pageable page) {
//		Page<CompanyEntity> pagedResult = companyRepository.findAll(page);
//		return pagedResult.hasContent() ?
//				pagedResult.getContent() :
//					new ArrayList<>();
		return Collections.emptyList();
	}

	public List<Company> findAllCompanies() {
//		return companyRepository.findAll();
		return Collections.emptyList();
	}
	
	@Override
	public Optional<Company> findById(Long companyId) {
		return null; //companyRepository.findById(companyId);
	}

	@Override
	public Company update(Company company) {
		return null;
				//companyRepository.save(company);
	}

	@Override
	public void remove(Long companyId) {
		companyRepository.deleteById(companyId);
	}

	public boolean isRegisteredNumberInvalid(String registeredNumber) {
//		CompanyEntity company = companyRepository.findTopCompanyByRegisteredNumber(registeredNumber);
		return false; //Optional.ofNullable(company).isPresent();
	}

	@Override
	public List<Company> findAllPageable(Pageable page, Long id) {
		return new ArrayList<>();
	}

	public List<Company> findCompanyByName(Pageable page, String name) {
		Page<CompanyEntity> pagedResult = companyRepository.getCompanyByNameRegisteredNumberOrEmail(page, name);
//		return pagedResult.hasContent() ?
//				pagedResult.getContent() :
//					new ArrayList<>();
		return Collections.emptyList();
	}

	public Optional<Company> findCompanyByTokenId(String tokenId) {
		return Optional.empty();//companyRepository.findCompanyByTokenId(tokenId);
	}
}