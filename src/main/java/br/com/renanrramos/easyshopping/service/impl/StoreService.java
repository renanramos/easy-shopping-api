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

	@Override
	public List<Store> findAll() {
		return storeRepository.findAll();
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
}
