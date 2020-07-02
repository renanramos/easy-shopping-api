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

import br.com.renanrramos.easyshopping.model.Administrator;
import br.com.renanrramos.easyshopping.repository.AdministratorRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class AdministratorService implements CommonService<Administrator>{

	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Override
	public Administrator save(Administrator administrator) {
		return administratorRepository.save(administrator);
	}

	@Override
	public List<Administrator> findAll() {
		return administratorRepository.findAll();
	}

	@Override
	public Optional<Administrator> findById(Long administratorId) {
		return administratorRepository.findById(administratorId);
	}

	@Override
	public Administrator update(Administrator administrator) {
		return administratorRepository.save(administrator);
	}

	@Override
	public void remove(Long administratorId) {
		administratorRepository.deleteById(administratorId);
	}

}
