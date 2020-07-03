/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Company> findAll() {
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

	public boolean isValidCompanyId(Long companyId) {
		return companyRepository.existsById(companyId);
	}
}