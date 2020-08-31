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

import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.repository.CustomerRepository;
import br.com.renanrramos.easyshopping.service.CommonService;

/**
 * @author renan.ramos
 *
 */
@Service
public class CustomerService implements CommonService<Customer>{

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> findAllPageable(Pageable page, Long id) {
		Page<Customer> pagedResult = customerRepository.findAll(page); 
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	@Override
	public Optional<Customer> findById(Long customerId) {
		return customerRepository.findById(customerId);
	}

	@Override
	public Customer update(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void remove(Long customerId) {
		customerRepository.deleteById(customerId);
	}

	public Optional<List<Customer>> findCustomerByCpf(String cpf) {
		List<Customer> customers = customerRepository.findCustomerByCpf(cpf); 
		return  Optional.of(customers);
	}

	@Override
	public List<Customer> findAll(Pageable page) {
		return new ArrayList<>();
	}

	public List<Customer> findCustomerByName(Pageable page, String name) {
		Page<Customer> pagedResult = customerRepository.findCustomerByNameContaining(page, name);
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
				new ArrayList<>();
	}
	
}
