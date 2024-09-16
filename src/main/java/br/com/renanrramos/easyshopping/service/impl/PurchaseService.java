/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
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

import br.com.renanrramos.easyshopping.model.Purchase;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.PurchaseRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class PurchaseService implements CommonService<Purchase> {

	@Autowired
	private PurchaseRepository purchaseRepository;

	@Override
	public Purchase save(Purchase purchase) {
		return purchaseRepository.save(purchase);
	}

	@Override
	public List<Purchase> findAll(Pageable page) {
		Page<Purchase> pagedResult = purchaseRepository.findAll(page);
		return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
	}

	@Override
	public Optional<Purchase> findById(Long purchaseId) {
		return purchaseRepository.findById(purchaseId);
	}

	@Override
	public Purchase update(Purchase purchase) {
		return purchaseRepository.save(purchase);
	}

	@Override
	public void remove(Long purchaseId) {
		purchaseRepository.deleteById(purchaseId);
	}

	@Override
	public List<Purchase> findAllPageable(Pageable page, Long id) {
		return null;
	}

	public List<Purchase> findPurchasesByCustomerId(String customerId) {
		return purchaseRepository.findPurchaseByCustomerId(customerId);
	}
}
