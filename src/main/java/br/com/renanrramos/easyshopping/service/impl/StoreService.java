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

import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.repository.StoreRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class StoreService implements CommonService<Store>{

	@Autowired
	private StoreRepository storeRepository;

	@Override
	public Store save(Store store) {
		return storeRepository.save(store);
	}

	public List<Store> findAllPageable(Pageable page, Long companyId) {
		Page<Store> pagedResult = 
				(companyId == null) ?
				storeRepository.findAll(page) :
					storeRepository.findStoreByCompanyId(page, companyId);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	@Override
	public List<Store> findAll(Pageable page) {
		return new ArrayList<>();
	}

	@Override
	public Optional<Store> findById(Long storeId) {
		return storeRepository.findById(storeId);
	}

	@Override
	public Store update(Store store) {
		return storeRepository.save(store);
	}

	@Override
	public void remove(Long storeId) {
		storeRepository.deleteById(storeId);
	}

	public List<Store> findStoreByName(Pageable page, String name) {
		Page<Store> pagedResult = 
				(name == null) ?
				storeRepository.findAll(page) :
				storeRepository.findStoreByNameContaining(page, name);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	public List<Store> findStoreByCompanyId(Pageable page, Long companyId) {
		Page<Store> pagedResult = storeRepository.findStoreByCompanyId(page, companyId);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}
}
