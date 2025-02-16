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

import br.com.renanrramos.easyshopping.interfaceadapter.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.CustomerRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class CustomerService implements CommonService<CustomerEntity>{

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public CustomerEntity save(CustomerEntity customerEntity) {
		return customerRepository.save(customerEntity);
	}

	@Override
	public List<CustomerEntity> findAllPageable(Pageable page, Long id) {
		Page<CustomerEntity> pagedResult = customerRepository.findAll(page);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	@Override
	public Optional<CustomerEntity> findById(Long customerId) {
		return customerRepository.findById(customerId);
	}

	@Override
	public CustomerEntity update(CustomerEntity customerEntity) {
		return customerRepository.save(customerEntity);
	}

	@Override
	public void remove(Long customerId) {
		customerRepository.deleteById(customerId);
	}

	public Optional<List<CustomerEntity>> findCustomerByCpf(String cpf) {
		List<CustomerEntity> customerEntities = customerRepository.findCustomerByCpf(cpf);
		return  Optional.of(customerEntities);
	}

	@Override
	public List<CustomerEntity> findAll(Pageable page) {
		return new ArrayList<>();
	}

	public List<CustomerEntity> findCustomerByName(Pageable page, String name) {
		Page<CustomerEntity> pagedResult = customerRepository.getCustomerByNameCPFOrEmail(page, name);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	public CustomerEntity findCustomerByTokenId(String tokenId) {
		return customerRepository.findCustomerByTokenId(tokenId);
	}
}
