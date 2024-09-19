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

import br.com.renanrramos.easyshopping.model.AdministratorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.AdministratorRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class AdministratorService implements CommonService<AdministratorEntity>{

	@Autowired
	private AdministratorRepository administratorRepository;

	public AdministratorService(AdministratorRepository administratorRepository) {
		this.administratorRepository = administratorRepository;
	}

	@Override
	public AdministratorEntity save(AdministratorEntity administratorEntity) {
		return administratorRepository.save(administratorEntity);
	}

	@Override
	public List<AdministratorEntity> findAllPageable(Pageable page, Long administratorId) {
		Page<AdministratorEntity> pagedResult = administratorRepository.findAll(page);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	@Override
	public Optional<AdministratorEntity> findById(Long administratorId) {
		return administratorRepository.findById(administratorId);
	}

	@Override
	public AdministratorEntity update(AdministratorEntity administratorEntity) {
		return administratorRepository.save(administratorEntity);
	}

	@Override
	public void remove(Long administratorId) {
		administratorRepository.deleteById(administratorId);
	}

	public List<AdministratorEntity> searchAdministratorByName(String name) {
		return administratorRepository.findAdministratorByNameContains(name);
	}

	@Override
	public List<AdministratorEntity> findAll(Pageable page) {
		return null;
	}
}
