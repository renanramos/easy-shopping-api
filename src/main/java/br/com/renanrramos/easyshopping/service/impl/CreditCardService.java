/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
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

import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.CreditCardRepositoy;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class CreditCardService implements CommonService<CreditCard>{

	@Autowired
	private CreditCardRepositoy creditCardRepository;
	
	@Override
	public CreditCard save(CreditCard creditCard) {
		return creditCardRepository.save(creditCard);
	}

	@Override
	public List<CreditCard> findAllPageable(Pageable page, Long customerId) {
		return new ArrayList<>();
	}

	public List<CreditCard> findAllPageable(Pageable page, String customerId) {
		Page<CreditCard> pagedResult = creditCardRepository.findCreditCardByCustomerId(page, customerId);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	@Override
	public Optional<CreditCard> findById(Long creditCardId) {
		return creditCardRepository.findById(creditCardId);
	}

	@Override
	public CreditCard update(CreditCard creditCard) {
		return creditCardRepository.save(creditCard);
	}

	@Override
	public void remove(Long creditCardId) {
		creditCardRepository.removeById(creditCardId);
	}

	@Override
	public List<CreditCard> findAll(Pageable page) {
		return new ArrayList<>();
	}
}
