/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.CompanyRepository;
import br.com.renanrramos.easyshopping.model.CompanyEntity;
import br.com.renanrramos.easyshopping.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author renan.ramos
 *
 */
@Service
public class CompanyService implements CommonService<CompanyEntity>{

	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public CompanyEntity save(CompanyEntity company) {
		return companyRepository.save(company);
	}

	@Override
	public List<CompanyEntity> findAll(Pageable page) {
		Page<CompanyEntity> pagedResult = companyRepository.findAll(page);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	public List<CompanyEntity> findAllCompanies() {
		return companyRepository.findAll();
	}
	
	@Override
	public Optional<CompanyEntity> findById(Long companyId) {
		return companyRepository.findById(companyId);
	}

	@Override
	public CompanyEntity update(CompanyEntity company) {
		return companyRepository.save(company);
	}

	@Override
	public void remove(Long companyId) {
		companyRepository.deleteById(companyId);
	}

	public boolean isRegisteredNumberInvalid(String registeredNumber) {
		CompanyEntity company = companyRepository.findTopCompanyByRegisteredNumber(registeredNumber);
		return Optional.ofNullable(company).isPresent();
	}

	@Override
	public List<CompanyEntity> findAllPageable(Pageable page, Long id) {
		return new ArrayList<>();
	}

	public List<CompanyEntity> findCompanyByName(Pageable page, String name) {
		Page<CompanyEntity> pagedResult = companyRepository.getCompanyByNameRegisteredNumberOrEmail(page, name);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	public Optional<CompanyEntity> findCompanyByTokenId(String tokenId) {
		return companyRepository.findCompanyByTokenId(tokenId);
	}
}