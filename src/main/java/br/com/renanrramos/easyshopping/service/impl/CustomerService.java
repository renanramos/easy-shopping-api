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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> findAllPageable(Integer pageNumber, Integer pageSize, String sortBy) {
		Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Customer> pagedResult = customerRepository.findAll(page); 
		return pagedResult.hasContent() ?
				pagedResult.getContent() :
					new ArrayList<>();
	}

	public Optional<Customer> findById(Long customerId) {
		return customerRepository.findById(customerId);
	}

	public Customer update(Customer customer) {
		return customerRepository.save(customer);
	}

	public void remove(Long customerId) {
		customerRepository.deleteById(customerId);
	}

	public boolean isCpfInvalid(String cpf) {
		return customerRepository.findTopCustomerByCpf(cpf) == null;
	}

	@Override
	public List<Customer> findAll() {
		return new ArrayList<>();
	}
}
